//package com.xsphere.xam.modules.auth.ui.widget
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawBehind
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.mealmate.extensions.HeightBox
//import com.example.mealmate.modules.auth.ui.widget.TermsAndPrivacyText
//import com.example.mealmate.ui.theme.CustomColors
//
//@Composable
//fun LoginView(onClickLogIn: () -> Unit, onClickPhoneLogin: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//    ) {
//        Column {
////            Column(
////                modifier = Modifier
////                    .weight(1f)
////                    .fillMaxSize()
////
////                    .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally
////            ) {
////                Text("Log in to XAM", fontWeight = FontWeight.W700, fontSize = 32.sp)
////                20.HeightBox()
////
////                AuthItem(
////                    onClick = {
////                        onClickPhoneLogin()
////                    },
////                    imageId = R.drawable.mobile,
////                    label = "Log in by phone number",
////                    modifier = Modifier.fillMaxWidth(),
////                    arrangement = Arrangement.Center
////                )
////                AuthItem(
////                    onClick = {},
////                    imageId = R.drawable.facebook,
////                    label = "Continue with Facebook",
////                    modifier = Modifier.fillMaxWidth(),
////                    arrangement = Arrangement.Center
////                )
////                AuthItem(
////                    onClick = {},
////                    imageId = R.drawable.google,
////                    label = "Continue with Google",
////                    modifier = Modifier.fillMaxWidth(),
////                    arrangement = Arrangement.Center
////                )
////                Spacer(
////                    modifier = Modifier.weight(1f)
////                )
////            }
//
//            TermsAndPrivacyText()
//            20.HeightBox()
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(CustomColors.cardColor)
//                    .drawBehind {
//                        val strokeWidth = 2.dp.toPx()
//                        val color = Color(0xffEDEDED)
//                        // Left border
//                        drawLine(
//                            color = color,
//                            start = Offset(0f, 0f),
//                            end = Offset(size.width, 0f),
//                            strokeWidth = strokeWidth
//                        )
//                    }) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(20.dp)
//                        .clickable {
//                            onClickLogIn()
//                        },
//                    horizontalArrangement = Arrangement.Center,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Don't have an account? ",
//                        color = CustomColors.textSecond,
//                        fontSize = 16.sp
//                    )
//                    Text(
//                        "Sign Up",
//                        color = CustomColors.textMain,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.W700
//                    )
//                }
//            }
//        }
//    }
//}