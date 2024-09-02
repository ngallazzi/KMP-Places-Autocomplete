package com.ngallazzi.places.presentation

internal data class PlaceAutocompleteState(
    val suggestions: List<String> = listOf(),
    val text: String = "",
    val isSuggestionsPopupExpanded: Boolean = false,
    val errorText: String? = null
)