package com.example.mealmate.modules.home.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealmate.modules.home.ui.viewmodel.HomeViewModel

@Composable
fun HomePage(appNavi: NavHostController = rememberNavController()) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    Column {
        Text(
            text = homeViewModel.isLoginLoading.collectAsState().toString()
        )
    }
}