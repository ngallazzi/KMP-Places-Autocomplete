package com.ngallazzi.kmpplaces

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun MaterialCustomTheme(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val colors = lightColors(
        primary = Color.Cyan,
        primaryVariant = Color.Yellow,
        secondary = Color.Transparent,
        secondaryVariant = Color.Red,
        background = Color.Magenta,
    )

    MaterialTheme(
        colors = colors, content = {
            Surface(modifier = modifier) {
                content.invoke()
            }
        }
    )
}