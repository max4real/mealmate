package com.example.mealmate.modules.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.modules.home.ui.viewmodel.HomeViewModel
import com.example.mealmate.modules.home.ui.widget.DropDownWidget
import com.example.mealmate.modules.home.ui.widget.MealDetailsDialog
import com.example.mealmate.modules.home.ui.widget.MealGridCard
import com.example.mealmate.shared.widget.CustomAppBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState


@Composable
fun HomePage(appNavi: NavHostController) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val userName = viewModel.userName.collectAsState()
    val categoryList = viewModel.categoryList.collectAsState()
    val isCategoryLoading = viewModel.isCategoryLoading.collectAsState()
    val categoryError = viewModel.categoryError.collectAsState()
    val mealList = viewModel.mealList.collectAsState()
    val mealListLoading = viewModel.isMealListLoading.collectAsState()
    val mealListError = viewModel.mealListError.collectAsState()
    val isAddToPlanLoading = viewModel.isAddToPlanLoading.collectAsState()
    val addToPlanError = viewModel.addToPlanError.collectAsState()

    val showDialog = viewModel.showDialog.collectAsState()
    val selectedMeal = remember { mutableStateOf<MealDetailModel?>(null) }

    val isRefreshing = viewModel.isPageRefreshing.collectAsState()
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }
    LaunchedEffect(isRefreshing.value) {
        swipeRefreshState.isRefreshing = isRefreshing.value
    }

    if (showDialog.value) {
        MealDetailsDialog(
            info = selectedMeal.value!!,
            isAddToPlanLoading = isAddToPlanLoading.value,
            onAddToMealPlan = { viewModel.addToPlan(selectedMeal.value!!) },
            onDismissRequest = {
                viewModel.updateShowDialog(false)
            },
            message = addToPlanError.value
        )
    }

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.pageRefresh()
        },
        indicatorPadding = PaddingValues(top = 56.dp)
    ) {
        Column {
            CustomAppBar(userName = userName.value)
            10.HeightBox()

            DropDownWidget(
                categoryList = categoryList.value,
                onSelected = { viewModel.getMealListWithCategory(it.id) },
                isLoading = isCategoryLoading.value
            )
            if (categoryError.value.isNotEmpty())
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart) {
                    Text(text = categoryError.value, color = Color.Red)
                }

            10.HeightBox()

            when {
                mealListLoading.value -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            strokeWidth = 2.dp,
                            color = Color.Black
                        )
                    }
                }

                mealListError.value.isNotEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = mealListError.value, color = Color.Red)
                    }
                }

                mealList.value.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No meals found.")
                    }
                }

                else -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = "Discover Recipes",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.W500,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }

                        items(mealList.value) { meal ->
                            MealGridCard(
                                mealDetailModel = meal,
                                onViewClick = {
                                    viewModel.updateShowDialog(!showDialog.value)
                                    selectedMeal.value = meal
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


