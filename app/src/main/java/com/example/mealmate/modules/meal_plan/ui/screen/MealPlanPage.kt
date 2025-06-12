package com.example.mealmate.modules.meal_plan.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.modules.meal_plan.data.model.MealPlanModel
import com.example.mealmate.modules.meal_plan.ui.viewmodel.MealPlanViewModel
import com.example.mealmate.modules.meal_plan.ui.widget.MealListCard
import com.example.mealmate.modules.meal_plan.ui.widget.MealPlanDialog
import com.example.mealmate.modules.meal_plan.ui.widget.MealPlanHeader
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.widget.CustomAppBar
import com.example.mealmate.shared.widget.LoadingDialog
import com.example.mealmate.shared.widget.SwipeToDismissContainer
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import to


@Composable
fun MealPlanPage(appNavi: NavHostController) {

    val viewModel = hiltViewModel<MealPlanViewModel>()
    val userName = viewModel.userName.collectAsState()
    val mealList = viewModel.mealList.collectAsState()
    val mealListLoading = viewModel.isMealListLoading.collectAsState()
    val mealListError = viewModel.mealListError.collectAsState()
    val showDialog = viewModel.showDialog.collectAsState()
    val showLoadingDialog = viewModel.showLoadingDialog.collectAsState()
    val selectedMeal = remember { mutableStateOf<MealPlanModel?>(null) }

    val isRefreshing = viewModel.isPageRefreshing.collectAsState()
    val swipeRefreshState = remember { SwipeRefreshState(isRefreshing = false) }
    LaunchedEffect(isRefreshing.value) {
        swipeRefreshState.isRefreshing = isRefreshing.value
    }

    val refreshFlag = appNavi.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("REFRESH_MEAL_PLAN", false)
        ?.collectAsState()

    LaunchedEffect(refreshFlag?.value) {
        if (refreshFlag?.value == true) {
            viewModel.pageRefresh()
            // reset so it won’t fire again on recomposition
            appNavi.currentBackStackEntry?.savedStateHandle?.set("REFRESH_MEAL_PLAN", false)
        }
    }

    if (showDialog.value) {
        MealPlanDialog(
            info = selectedMeal.value!!,
            onDismissRequest = {
                viewModel.updateShowDialog(false)
            },
            onSave = { updatedPlan ->
                viewModel.updateShowDialog(false)
                viewModel.updateMealPlan(updatedPlan)
            }
        )
    }

    if (showLoadingDialog.value) {
        LoadingDialog()
    }
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = {
            viewModel.pageRefresh()
        },
        indicatorPadding = PaddingValues(top = 56.dp)
    ) {
        Column {
            CustomAppBar(
                userName = userName.value
            )
            MealPlanHeader(
                onClickAddMeal = {
                    appNavi.to(Screen.NewMeal.route)
                },
            )
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

                mealList.value.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No meals found.")
                    }
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = mealList.value, key = { it.id }) { meal ->
                            SwipeToDismissContainer(
                                item = meal,
                                onDismiss = { dismissedMeal ->
                                    viewModel.showCardRemoveDialog(meal = dismissedMeal)
                                },
                                directions = setOf(DismissDirection.EndToStart),
                                dismissThreshold = 0.7f,
                            ) {
                                MealListCard(
                                    info = meal, onViewClick = {
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
}
