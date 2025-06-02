package com.example.mealmate.modules.auth.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mealmate.extensions.HeightBox
import com.example.mealmate.modules.auth.ui.viewmodel.RegisterViewModel
import com.example.mealmate.modules.auth.ui.widget.OtpBox
import com.example.mealmate.ui.theme.CustomColors
import compose.icons.TablerIcons
import compose.icons.tablericons.ChevronLeft

@Composable
fun OTPPage(
    appNavi: NavHostController, registerViewModel: RegisterViewModel
) {
    val contentPadding: Dp = 20.dp
    val currentOtp by registerViewModel.currentOtp.collectAsState()
    val remainingSeconds by registerViewModel.remainingSeconds.collectAsState()
    val showErrorMessage by registerViewModel.showErrorMessage.collectAsState()
    val errorMessage by registerViewModel.errorMessage.collectAsState()
    val isEnable by registerViewModel.isEnable.collectAsState()
    val isOtpBtnEnable by registerViewModel.isOtpBtnEnable.collectAsState()
    val canResend by registerViewModel.canResend.collectAsState()
    val resending by registerViewModel.resending.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
    ) {
        IconButton(onClick = { appNavi.popBackStack() }) {
            Icon(TablerIcons.ChevronLeft, contentDescription = "back")
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            20.HeightBox()
            Text(
                modifier = Modifier.padding(horizontal = contentPadding),
                text = "Enter 6-digit code",
                fontSize = 32.sp,
                fontWeight = FontWeight.W700
            )
            5.HeightBox()
            Text(
                modifier = Modifier.padding(horizontal = contentPadding),
                text = "Your code was sent to ${registerViewModel.email.collectAsState().value}",
                fontSize = 16.sp,
                color = CustomColors.textSecond.copy(0.6f),
                textAlign = TextAlign.Center
            )
            20.HeightBox()
            OtpBox(
                isEnable = isEnable,
                currentOtp = currentOtp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = contentPadding),
                onValueChange = {
                    registerViewModel.onOtpValueChange(it)
                    registerViewModel.setShowErrorMessage(false)
                },
            )
            10.HeightBox()
            if (showErrorMessage) Text(
                modifier = Modifier.padding(horizontal = contentPadding),
                text = errorMessage,
                fontSize = 16.sp,
                color = CustomColors.red,
                textAlign = TextAlign.Center
            )
            20.HeightBox()
            Row(
                modifier = Modifier.padding(horizontal = contentPadding)
            ) {
                Text(
                    modifier = if (canResend) Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            registerViewModel.resendOtp()
                        }
                    else Modifier,
                    text = "Resend Code ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    color = if (canResend) CustomColors.textMain
                    else CustomColors.textSecond.copy(0.6f),
                    textAlign = TextAlign.Center)
                if (resending) CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = CustomColors.textMain,
                    strokeWidth = 2.dp
                )
                else Text(
                    text = "$remainingSeconds s",
                    fontSize = 16.sp,
                    color = CustomColors.textSecond.copy(0.6f),
                    textAlign = TextAlign.Center
                )
            }
            40.HeightBox()
            Button(
                enabled = isOtpBtnEnable,
                onClick = {
                    registerViewModel.onOtpBtnClick()
                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContentColor = Color.White.copy(0.5f),
                    disabledContainerColor = CustomColors.textSecond
                )
            ) {
//                if (isLoading.value) CircularProgressIndicator(
//                    modifier = Modifier.size(20.dp), strokeWidth = 2.dp, color = Color.White
//                )
//                else
                Text("Continue")
            }
        }
    }
}
