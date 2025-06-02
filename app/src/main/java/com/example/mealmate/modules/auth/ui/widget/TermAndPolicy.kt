package com.example.mealmate.modules.auth.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mealmate.ui.theme.CustomColors

@Composable
fun TermsAndPrivacyText(modifier: Modifier = Modifier) {
    val linkHandler = LocalUriHandler.current

    val annotatedText = buildAnnotatedString {
        append("By continuing, you agree to our ")

        pushStyle(
            SpanStyle(
                color = CustomColors.textMain,
            )
        )
        pushLink(LinkAnnotation.Url("https://example.com/terms"))
        append("Terms of Service")
        pop()
        pop()

        append(" and confirm that you have read our ")

        pushStyle(
            SpanStyle(

                color = CustomColors.textMain,
            )
        )
        pushLink(LinkAnnotation.Url("https://example.com/privacy"))
        append("Privacy Policy")
        pop()
        pop()

        append(".")
    }

    BasicText(
        text = annotatedText,
        style = TextStyle(
            fontSize = 14.sp,
            color = CustomColors.textSecond.copy(0.6f),
            textAlign = TextAlign.Center
        ),
        modifier = modifier.padding(horizontal = 20.dp)
    )

}
