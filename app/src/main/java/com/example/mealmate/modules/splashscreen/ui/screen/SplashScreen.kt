package com.example.mealmate.modules.splashscreen.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.R
import com.example.mealmate.modules.splashscreen.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(appNavi: NavHostController) {
    val viewModel = hiltViewModel<SplashViewModel>()

    LaunchedEffect(Unit) {
        viewModel.checkLoginAndNavigate(appNavi)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(300.dp),
            painter = painterResource(R.drawable.icon_txt),
            contentDescription = "App Logo"
        )
    }
}


