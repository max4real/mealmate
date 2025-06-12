package com.example.mealmate.modules.shopping_list.ui.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.shopping_list.ui.viewmodel.ShoppingListViewModel
import com.example.mealmate.modules.shopping_list.ui.widget.DelegateDialog
import com.example.mealmate.modules.shopping_list.ui.widget.ShoppingListCard
import com.example.mealmate.modules.shopping_list.ui.widget.ShoppingListHeader
import com.example.mealmate.shared.widget.CustomAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun ShoppingListPage(appNavi: NavHostController) {
    val viewModel = hiltViewModel<ShoppingListViewModel>()
    val userName = viewModel.userName.collectAsState()
    val shoppingList = viewModel.shoppingList.collectAsState()
    val mealListLoading = viewModel.isShoppingListLoading.collectAsState()
    val mealListError = viewModel.shoppingListError.collectAsState()
    val showLoadingDialog = viewModel.showLoadingDialog.collectAsState()
    val isRefreshing = viewModel.isPageRefreshing.collectAsState()
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }
    val showDialog = viewModel.showDialog.collectAsState()

    LaunchedEffect(isRefreshing.value) {
        swipeRefreshState.isRefreshing = isRefreshing.value
    }

    if (showDialog.value) {
        DelegateDialog(
            info = shoppingList.value, onDismissRequest = { viewModel.updateShowDialog(false) })
    }

    SwipeRefresh(
        state = swipeRefreshState, onRefresh = {
            viewModel.pageRefresh()
        }, indicatorPadding = PaddingValues(top = 56.dp)
    ) {
        Column {
            CustomAppBar(
                userName = userName.value
            )
            ShoppingListHeader(
                onClickDelegate = {
                    viewModel.updateShowDialog(!showDialog.value)
                },
            )
            10.HeightBox()
            when {
                mealListLoading.value -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp), strokeWidth = 2.dp, color = Color.Black
                        )
                    }
                }

                mealListError.value.isNotEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = mealListError.value, color = Color.Red)
                    }
                }

                shoppingList.value.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No shopping list yet.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        shoppingList.value.forEach { shoppingListItem ->
                            item {
                                Text(
                                    text = "Meal: ${shoppingListItem.ingredients.firstOrNull()?.recipeName ?: "Unnamed"}",
                                    modifier = Modifier.padding(vertical = 4.dp)
                                )
                            }

                            shoppingListItem.ingredients.forEach { ingredient ->
                                item {
                                    ShoppingListCard(
                                        ingredient = ingredient, onCheckedChange = { isChecked ->
                                            viewModel.onIngredientChecked(
                                                shoppingListItem.id, ingredient, isChecked
                                            )
                                        })
                                }
                            }

                            item {
                                10.HeightBox()
                            }
                        }
                    }
                }

            }

        }
    }

}