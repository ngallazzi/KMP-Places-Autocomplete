package com.ngallazzi.places.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
internal fun DefaultTextField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
    textStyle: TextStyle = LocalTextStyle.current,
    borderRadius: Dp = 16.dp
) {
    OutlinedTextField(
        modifier = modifier.background(color = Color.Transparent),
        value = value,
        onValueChange = { newValue ->
            if (newValue.text != value.text)
                onValueChange(newValue)
        },
        singleLine = true,
        textStyle = textStyle,
        label = { Text(label) },
        shape = RoundedCornerShape(borderRadius),
        keyboardOptions = keyboardOptions,
    )
}