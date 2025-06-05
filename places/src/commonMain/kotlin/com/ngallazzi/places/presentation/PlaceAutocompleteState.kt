package com.ngallazzi.places.presentation

import androidx.compose.ui.text.input.TextFieldValue
import com.ngallazzi.places.domain.Suggestion

internal data class PlaceAutocompleteState(
    val suggestions: List<Suggestion> = listOf(),
    val textFieldValue: TextFieldValue = TextFieldValue(),
    val isSuggestionsPopupExpanded: Boolean = false,
    val error: Throwable? = null
)