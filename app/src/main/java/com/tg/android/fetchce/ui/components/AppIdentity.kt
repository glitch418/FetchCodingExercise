package com.tg.android.fetchce.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Box which changes its scaling with animated background opacity
 */
@Composable
fun AppBox(alpha: Animatable<Float, AnimationVector1D>) {
    val scaleAnimation = rememberInfiniteTransition()
    val scale by scaleAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
    )

    Box(
        modifier = Modifier
            .size(120.dp * scale)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFD4AF37).copy(alpha = alpha.value))
            .padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(18.dp))
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
            Text(
                text = "T",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AppText(
    titleAlpha: Animatable<Float, AnimationVector1D>,
    taglineAlpha: Animatable<Float, AnimationVector1D>
) {
    Text(
        text = "Tejas Gupta",
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.graphicsLayer { alpha = titleAlpha.value }
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        text = "tgupta39@wisc.edu",
        color = Color.White.copy(alpha = 0.9f),
        fontSize = 15.sp,
        modifier = Modifier.graphicsLayer { alpha = taglineAlpha.value }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBox() {
    val alpha = remember { Animatable(1f) }
    AppBox(alpha)
}

@Preview(showBackground = true)
@Composable
fun PreviewAppText() {
    val titleAlpha = remember { Animatable(1f) }
    val taglineAlpha = remember { Animatable(1f) }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        AppText(titleAlpha, taglineAlpha)
    }
}