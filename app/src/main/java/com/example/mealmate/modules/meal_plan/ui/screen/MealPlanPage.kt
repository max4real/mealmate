package com.example.mealmate.modules.meal_plan.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.modules.meal_plan.data.model.MealItem
import com.example.mealmate.modules.meal_plan.ui.viewmodel.MealPlanViewModel
import com.example.mealmate.modules.meal_plan.ui.widget.MealListCard
import com.example.mealmate.modules.meal_plan.ui.widget.MealPlanHeader
import com.example.mealmate.navigation.Screen
import com.example.mealmate.shared.widget.CustomAppBar
import com.example.mealmate.shared.widget.SwipeToDismissContainer
import to


@Composable
fun MealPlanPage(appNavi: NavHostController) {
    val image: String =
        "https://media.istockphoto.com/id/1472680285/photo/healthy-meal-with-grilled-chicken-rice-salad-and-vegetables-served-by-woman.jpg?s=612x612&w=0&k=20&c=E4Y94oLIj8lXYk0OovBhsah3s_sC--WF95xPDvbJPlU="
    val viewModel = hiltViewModel<MealPlanViewModel>()
    val userName = viewModel.userName.collectAsState()
    val showDialog = remember { mutableStateOf(false) }

    val meals = remember {
        mutableStateListOf(
            MealItem(
                "1",
                "Grilled Chicken",
                image
            ),
            MealItem("2", "Vegetable Salad", image),
            MealItem("3", "Vegetable Salad", image),
            MealItem("4", "Vegetable Salad", image),
            MealItem("5", "Vegetable Salad", image),
            MealItem("6", "Vegetable Salad", image),
        )
    }

    Column {
        CustomAppBar(
            userName = userName.value
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                MealPlanHeader(
                    onClickAddMeal = {
                        appNavi.to(Screen.NewMeal.route)
                    },
                )
            }
            items(
                items = meals,
                key = { it.id }
            ) { meal ->
                SwipeToDismissContainer(
                    item = meal,
                    onDismiss = { dismissedMeal ->
                        meals.remove(dismissedMeal)
                        viewModel.showCardRemoveDialog()
                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThreshold = 0.7f,
                ) {
                    MealListCard(
                        image = meal.imageUrl,
                        mealName = meal.name,
                        onViewClick = {}
                    )
                }
            }
        }
    }
}
