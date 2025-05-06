package com.ngallazzi.places.presentation

import KMPPlaces.places.BuildConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.window.PopupProperties
import com.ngallazzi.places.domain.Place
import com.ngallazzi.places.domain.PlaceDetails
import kotlinx.coroutines.launch
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
    enabled: Boolean = true,
    readOnly: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    languageCode: String = Locale.current.language,
    textStyle: TextStyle = LocalTextStyle.current,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
    shape: Shape = OutlinedTextFieldDefaults.shape,
    onSuggestionSelected: suspend (PlaceDetails) -> Unit,
    onClearText: () -> Unit = {}
) {
    val helper = remember {
        PlacesHelper(BuildConfig.API_KEY)
    }
    val coroutineScope = rememberCoroutineScope()

    val viewModel = remember(key1 = text) {
        PlaceAutoCompleteTextFieldModel(
            placeType = type,
            helper = helper,
            languageCode = languageCode,
            initialText = text
        )
    }
    val state = viewModel.uiState.collectAsState()

    val outlinedFieldModifier = modifier.fillMaxWidth()
    val actualLabel = @Composable { Text(label) }
    val dropDownMenuContent: @Composable ColumnScope.() -> Unit = {
        state.value.suggestions.forEach { suggestion ->
            DropdownMenuItem(modifier = Modifier.fillMaxWidth(), onClick = {
                coroutineScope.launch {
                    viewModel.onSuggestionSelected(suggestion, onPlaceDetailsRetrieved = {
                        onSuggestionSelected(it)
                    })
                }
            }, text = { Text(suggestion.description) })
        }
    }

    Box {
        OutlinedTextField(
            modifier = outlinedFieldModifier,
            value = state.value.textFieldValue,
            onValueChange = { newValue: TextFieldValue ->
                viewModel.onValueChange(newValue)
            },
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
            trailingIcon = {
                if (text.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.onClearText()
                        onClearText()
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear text")
                    }
                }
            },
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