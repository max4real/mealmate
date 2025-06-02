package com.example.mealmate.modules.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomePage(appNavi: NavHostController = rememberNavController()) {
    Column {
        Text(
            "this is home"
        )
    }
}