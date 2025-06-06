package com.example.mealmate.modules.new_meal.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.auth.ui.widget.CustomInputField
import com.example.mealmate.modules.new_meal.ui.viewmodel.NewMealViewModel
import com.example.mealmate.shared.widget.CustomAppBar
import com.example.mealmate.ui.theme.CustomColors


@Composable
fun NewMealPage(appNavi: NavHostController) {
    val viewModel = hiltViewModel<NewMealViewModel>()
    val userName = viewModel.userName.collectAsState()

    Column(modifier = Modifier.systemBarsPadding()) {
        CustomAppBar(
            userName = userName.value,
            showLogo = false,
            includeBackKey = true,
            onClickBack = {
                appNavi.popBackStack()
            }
        )
        10.HeightBox()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item() {
                Text(
                    text = "Create Custom Recipe",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            item() {
                Column {
                    Text(
                        text = "Meal Name",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    CustomInputField(
                        label = "Meal name",
                        value = "email.value",
                        onValueChange = {},
                        keyboardType = KeyboardType.Text
                    )
                }
            }
            item() {
                Column {
                    Text(
                        text = "Instructions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    //add textfield
                    CustomInputField(
                        label = "Meal name",
                        value = "email.value",
                        onValueChange = {},
                        keyboardType = KeyboardType.Text
                    )
                }
            }
            item {
                Button(
                    enabled = true,
                    onClick = {
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp),
                    colors = ButtonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContentColor = Color.White.copy(0.5f),
                        disabledContainerColor = CustomColors.textSecond
                    )
                ) {
                    if (false)
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(20.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    else
                        Text("Continue")
                }
            }
        }
    }
}
