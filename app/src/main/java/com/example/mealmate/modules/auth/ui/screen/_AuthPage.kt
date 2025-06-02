//package com.example.mealmate.modules.auth.ui.screen
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.systemBarsPadding
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.rememberNavController
//import com.example.mealmate.extensions.HeightBox
//import com.example.mealmate.modules.auth.ui.viewmodel.RegisterViewModel
//import com.example.mealmate.navigation.Screen
//import com.xsphere.xam.modules.auth.ui.widget.LoginView
//import com.example.mealmate.modules.auth.ui.screen.SignUpView
//import compose.icons.TablerIcons
//import compose.icons.tablericons.CircleMinus
//import kotlinx.coroutines.launch
//
//@Composable
//fun AuthPage(
//    appNavi: NavHostController = rememberNavController(),
//    registerViewModel: RegisterViewModel
//) {
//    val pagerState = rememberPagerState { 2 }
//    val scope = rememberCoroutineScope()
//
//    val pages = listOf<@Composable () -> Unit>(
//        {
//            SignUpView(
//                registerViewModel = registerViewModel,
//                onClickSignUp = {
//                    scope.launch {
//                        pagerState.animateScrollToPage(1)
//                    }
//                },
//                appNavi = appNavi
//            )
//        },
//        {
//            LoginView(
//                onClickLogIn = {
//                    scope.launch {
//                        pagerState.animateScrollToPage(0)
//                    }
//                },
//                onClickPhoneLogin = {
//                    appNavi.to(Screen.LoginScreen.route)
//                }
//            )
//        }
//    )
//
////    Box(
////        modifier = Modifier
////            .fillMaxSize()
////            .systemBarsPadding()
////            .background(Color.White),
////    ) {
////        Column(horizontalAlignment = Alignment.End, modifier = Modifier.fillMaxSize()) {
////
////            IconButton(onClick = {
////                appNavi.popBackStack()
////            }, modifier = Modifier.padding(0.dp)) {
////                Icon(TablerIcons.CircleMinus, contentDescription = "close")
////            }
////
////            50.HeightBox()
////            HorizontalPager(
////                state = pagerState,
////                userScrollEnabled = false,
////                modifier = Modifier.weight(1f)
////            ) {
////                pages[it]()
////            }
////
////        }
////    }
//}