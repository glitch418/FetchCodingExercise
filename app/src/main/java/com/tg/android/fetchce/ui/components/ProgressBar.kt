package com.tg.android.fetchce.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Loading progress bar for Splash Screen
 */
@Composable
fun ProgressBar(progressAnim: Animatable<Float, AnimationVector1D>) {
    LinearProgressIndicator(
        progress = { progressAnim.value },
        modifier = Modifier
            .width(200.dp)
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp)),
        trackColor = Color.White.copy(alpha = 0.2f),
        color = Color.White
    )
}
