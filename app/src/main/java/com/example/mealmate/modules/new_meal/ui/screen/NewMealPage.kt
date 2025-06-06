package com.example.mealmate.modules.new_meal.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.modules.new_meal.ui.viewmodel.NewMealViewModel
import com.example.mealmate.shared.widget.CustomAppBar


@Composable
fun NewMealPage(appNavi: NavHostController) {
    val viewModel = hiltViewModel<NewMealViewModel>()
    val userName = viewModel.userName.collectAsState()

    Column(modifier = Modifier.systemBarsPadding()) {
        CustomAppBar(
            userName = userName.value,
            includeBackKey = true,
            onClickBack = {
                appNavi.popBackStack()
            }
        )
    }
}
