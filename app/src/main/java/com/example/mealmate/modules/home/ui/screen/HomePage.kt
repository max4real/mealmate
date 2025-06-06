package com.example.mealmate.modules.home.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.home.data.model.CategoryModel
import com.example.mealmate.modules.home.ui.viewmodel.HomeViewModel
import com.example.mealmate.modules.home.ui.widget.DropDownWidget
import com.example.mealmate.shared.widget.CustomAppBar
import com.example.mealmate.modules.home.ui.widget.MealGridCard
import com.example.mealmate.modules.home.data.model.MealDetailModel
import com.example.mealmate.modules.home.ui.widget.MealDetailsDialog


@Composable
fun HomePage(appNavi: NavHostController) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val userName = homeViewModel.userName.collectAsState()

    val showDialog = remember { mutableStateOf(false) }
    if (showDialog.value) {
        MealDetailsDialog(
            info = MealDetailModel(
                mealId = "1",
                mealName = "Chicken Curry",
                mealImage = "https://media.istockphoto.com/id/1472680285/photo/healthy-meal-with-grilled-chicken-rice-salad-and-vegetables-served-by-woman.jpg?s=612x612&w=0&k=20&c=E4Y94oLIj8lXYk0OovBhsah3s_sC--WF95xPDvbJPlU=",
                ingredients = listOf("Chicken", "Onions", "Garlic", "Spices"),
                instruction = "Step 1: Do this\nStep 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...Step 1: Do this\n" +
                        "Step 2: Do that...",
            ),
            isAddToPlanLoading = false,
            onAddToMealPlan = { /* handle plan */ },
            onDismissRequest = { showDialog.value = false }
        )
    }

    val categoryList = listOf(
        CategoryModel(
            name = "beef",
            id = "1",
            image = "https://images.immediate.co.uk/production/volatile/sites/30/2013/05/Aubergine-and-sesame-noodles-6138de6.jpg?quality=90&resize=556,505"
        ),
        CategoryModel(
            name = "chicken",
            id = "2",
            image = "https://images.immediate.co.uk/production/volatile/sites/30/2013/05/Aubergine-and-sesame-noodles-6138de6.jpg?quality=90&resize=556,505"
        ),
        CategoryModel(
            name = "vegan",
            id = "3",
            image = "https://images.immediate.co.uk/production/volatile/sites/30/2013/05/Aubergine-and-sesame-noodles-6138de6.jpg?quality=90&resize=556,505"
        ),
        CategoryModel(
            name = "Dassert",
            id = "4",
            image = "https://images.immediate.co.uk/production/volatile/sites/30/2013/05/Aubergine-and-sesame-noodles-6138de6.jpg?quality=90&resize=556,505"
        ),
        CategoryModel(
            name = "Soup",
            id = "5",
            image = "https://images.immediate.co.uk/production/volatile/sites/30/2013/05/Aubergine-and-sesame-noodles-6138de6.jpg?quality=90&resize=556,505"
        ),
    )

    Column {
        CustomAppBar(
            userName = userName.value
        )
        10.HeightBox()
        DropDownWidget(
            categoryList = categoryList,
            onSelected = { category -> }
        )
        10.HeightBox()
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
            items(10) {
                MealGridCard(
                    imageUrl = "https://media.istockphoto.com/id/1472680285/photo/healthy-meal-with-grilled-chicken-rice-salad-and-vegetables-served-by-woman.jpg?s=612x612&w=0&k=20&c=E4Y94oLIj8lXYk0OovBhsah3s_sC--WF95xPDvbJPlU=",
                    mealName = "Grilled Chicken",
                    onViewClick = { showDialog.value = !showDialog.value }
                )
            }
        }
    }
}
