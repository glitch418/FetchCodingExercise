package com.tg.android.fetchce.ui.screens.splash

import ProgressBar
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tg.android.fetchce.data.model.Item
import com.tg.android.fetchce.ui.components.AppBox
import com.tg.android.fetchce.ui.components.AppText
import com.tg.android.fetchce.ui.components.ItemRow
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(durationMillis: Int, onFinish: () -> Unit) {
    val progressAnim = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val titleAlpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        logoAlpha.animateTo(1f, animationSpec = tween(durationMillis / 4))
        titleAlpha.animateTo(1f, animationSpec = tween(durationMillis / 4))
        delay((durationMillis / 10).toLong())
        taglineAlpha.animateTo(1f, animationSpec = tween(durationMillis / 4))
        progressAnim.animateTo(targetValue = 1f, animationSpec = tween(durationMillis))
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF6A0DAD),
                        Color(0xFF000000)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            AppBox(logoAlpha)
            Spacer(modifier = Modifier.height(20.dp))
            AppText(titleAlpha, taglineAlpha)
            Spacer(modifier = Modifier.height(24.dp))
            ProgressBar(progressAnim)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSplashScreen() {
    SplashScreen(0) { }
}



