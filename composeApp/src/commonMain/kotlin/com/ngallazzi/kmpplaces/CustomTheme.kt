package com.ngallazzi.kmpplaces

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle

@Composable
fun Material3CustomTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(surface = Color.White), content = {
            content.invoke()
        }, typography = Typography(bodyMedium = TextStyle(fontStyle = FontStyle.Italic))
    )
}