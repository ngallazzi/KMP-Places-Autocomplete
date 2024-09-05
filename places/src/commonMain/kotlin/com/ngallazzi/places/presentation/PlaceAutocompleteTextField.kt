package com.ngallazzi.places.presentation

import KMPPlaces.places.BuildConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.window.PopupProperties
import com.ngallazzi.places.domain.Place
import kotlin.reflect.KClass

@Composable
fun PlaceAutoCompleteTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    text: String = "",
    type: KClass<out Place>,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    isExtendedModeActive: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    languageCode: String = Locale.current.language,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    onSuggestionSelected: (String) -> Unit = {},
) {
    val helper = remember {
        PlacesHelper(BuildConfig.API_KEY)
    }

    val viewModel = remember {
        PlaceAutoCompleteTextFieldModel(
            placeType = type,
            helper = helper,
            languageCode = languageCode,
            initialText = text,
            isExtendedModeActive = isExtendedModeActive
        )
    }
    val state = viewModel.uiState.collectAsState()
    var input by remember {
        mutableStateOf(
            TextFieldValue(
                state.value.text,
                selection = TextRange(state.value.text.length)
            )
        )
    }
    val outlinedFieldModifier = modifier.fillMaxWidth()
    val onValueChange = { newValue: TextFieldValue ->
        if (newValue.text != input.text) {
            input = newValue
            viewModel.onValueChange(newValue.text)
        }
    }
    val actualLabel = @Composable { Text(label) }
    val dropDownMenuContent: @Composable ColumnScope.() -> Unit = {
        state.value.suggestions.forEach { suggestion ->
            DropdownMenuItem(modifier = Modifier.fillMaxWidth(), onClick = {
                viewModel.onSuggestionSelected(suggestion)
                onSuggestionSelected(suggestion)
                input = TextFieldValue(suggestion, selection = TextRange(suggestion.length))
            }, text = { Text(suggestion) })
        }
    }

    Box {
        OutlinedTextField(
            modifier = outlinedFieldModifier,
            value = input,
            onValueChange = onValueChange,
            singleLine = true,
            label = actualLabel,
            keyboardOptions = keyboardOptions,
            textStyle = textStyle,
            colors = colors,
            shape = shape,
            keyboardActions = keyboardActions,
            maxLines = maxLines,
            minLines = minLines,
            enabled = enabled,
            readOnly = readOnly,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = supportingText
        )
        DropdownMenu(
            properties = PopupProperties(focusable = false, dismissOnClickOutside = true),
            expanded = state.value.isSuggestionsPopupExpanded, onDismissRequest = {
                viewModel.onSuggestionPopupDismissRequested()
            }, content = dropDownMenuContent
        )
    }
    state.value.errorText?.let {
        Text("An error occurred: $it")
    }
}