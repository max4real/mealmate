package com.example.mealmate.modules.shopping_list.ui.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.modules.home.ui.viewmodel.HomeViewModel
import com.example.mealmate.modules.shopping_list.ui.viewmodel.ShoppingListViewModel

@Composable
fun ShoppingListPage(navController: NavHostController) {
    val viewModel = hiltViewModel<ShoppingListViewModel>()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Shopping List Page")
    }
}