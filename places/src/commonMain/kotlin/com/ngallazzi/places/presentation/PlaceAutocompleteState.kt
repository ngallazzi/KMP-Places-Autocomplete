package com.ngallazzi.places.presentation

import androidx.compose.ui.text.input.TextFieldValue

internal data class PlaceAutocompleteState(
    val suggestions: List<String> = listOf(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val isSuggestionsPopupExpanded: Boolean = false,
    val errorText: String? = null
)