package com.ngallazzi.places.presentation

import KMPPlaces.places.BuildConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
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
    isExtendedModeActive: Boolean = false,
    languageCode: String = Locale.current.language
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
        mutableStateOf(TextFieldValue(state.value.text))
    }

    Box {
        DefaultTextField(modifier = modifier.fillMaxWidth(),
            label = label,
            keyboardOptions = keyboardOptions,
            value = input,
            onValueChange = { value ->
                input = value
                viewModel.onValueChange(value.text)
            })
        DropdownMenu(
            properties = PopupProperties(focusable = false, dismissOnClickOutside = true),
            expanded = state.value.isSuggestionsPopupExpanded, onDismissRequest = {
                viewModel.onSuggestionPopupDismissRequested()
            }, content = {
                state.value.suggestions.forEach { suggestion ->
                    DropdownMenuItem(modifier = Modifier.fillMaxWidth(), onClick = {
                        viewModel.onSuggestionSelected(suggestion)
                        input = TextFieldValue(suggestion, selection = TextRange(suggestion.length))
                    }, text = { Text(suggestion) })
                }
            })
    }
    state.value.errorText?.let {
        Text("An error occurred: $it")
    }
}