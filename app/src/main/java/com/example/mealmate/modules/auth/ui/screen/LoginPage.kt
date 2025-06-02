package com.example.mealmate.modules.auth.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mealmate.R
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.auth.ui.viewmodel.LoginViewmodel
import com.example.mealmate.modules.auth.ui.widget.CustomInputField
import com.example.mealmate.modules.auth.ui.widget.CustomPasswordField
import com.example.mealmate.modules.auth.ui.widget.TermsAndPrivacyText
import com.example.mealmate.navigation.Screen
import com.example.mealmate.ui.theme.CustomColors
import offAll

@Composable
fun LoginPage(
    appNavi: NavHostController = rememberNavController(),
    loginViewmodel: LoginViewmodel
) {
    val errorMessage = loginViewmodel.loginErrorMessage.collectAsState()
    val email = loginViewmodel.email.collectAsState()
    val password = loginViewmodel.password.collectAsState()
    val isLoading = loginViewmodel.isLoginLoading.collectAsState()

    var showPassword by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .background(Color.White),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(R.drawable.icon),
                    contentDescription = "App Logo",
                )
                10.HeightBox()
                Text("Welcome To MealMate", fontWeight = FontWeight.W700, fontSize = 28.sp)
                20.HeightBox()
                CustomInputField(
                    label = "Email",
                    value = email.value,
                    onValueChange = loginViewmodel::setEmail,
                    keyboardType = KeyboardType.Email
                )
                15.HeightBox()
                CustomPasswordField(
                    value = password.value,
                    label = "Password",
                    isVisible = showPassword,
                    onToggleVisibility = { showPassword = !showPassword },
                    onValueChange = loginViewmodel::setPassword
                )

                10.HeightBox()
                if (errorMessage.value.isNotEmpty())
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                    {
                        10.HeightBox()
                        Text(
                            errorMessage.value,
                            color = CustomColors.red,
                            fontWeight = FontWeight.W700
                        )
                    }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
                    onClick = {
                        loginViewmodel.onClickLoginBtn(appNavi)
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
                    if (isLoading.value)
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
            TermsAndPrivacyText()
            20.HeightBox()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        indication = rememberRipple(color = Color.LightGray.copy(alpha = 0.3f)),
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        appNavi.offAll(Screen.SignupScreen.route)
                    }
                    .background(CustomColors.cardColor)
                    .drawBehind {
                        drawLine(
                            Color(0xffEDEDED),
                            Offset(0f, 0f),
                            Offset(size.width, 0f),
                            strokeWidth = 2.dp.toPx()
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Don't have an account? ",
                        color = CustomColors.textSecond,
                        fontSize = 16.sp
                    )
                    Text(
                        "Sign Up",
                        color = CustomColors.textMain,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W700
                    )
                }
            }

        }
    }
}