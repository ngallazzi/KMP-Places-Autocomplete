package com.ngallazzi.places.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.domain.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

internal class PlaceAutoCompleteTextFieldModel(
    private val helper: PlacesHelper,
    private val placeType: KClass<out Place>,
    private val languageCode: String,
    initialText: String,
    private val isExtendedModeActive: Boolean = false
) : ViewModel() {
    private val _uiState = MutableStateFlow(PlaceAutocompleteState(text = initialText))
    val uiState: StateFlow<PlaceAutocompleteState> = _uiState.asStateFlow()

    fun onValueChange(text: String) {
        if (text.isNotEmpty()) {
            viewModelScope.launch {
                getSuggestions(
                    text,
                    type = placeType,
                    languageCode = languageCode
                ).fold(onSuccess = { places ->
                    _uiState.value = _uiState.value.copy(
                        suggestions = places.map { if (isExtendedModeActive) it.extendedLabel else it.label }
                            .distinct(),
                        isSuggestionsPopupExpanded = places.isNotEmpty()
                    )
                }, onFailure = {
                    _uiState.value = _uiState.value.copy(errorText = it.message.orEmpty())
                })
            }
        } else {
            _uiState.value =
                _uiState.value.copy(suggestions = listOf(), isSuggestionsPopupExpanded = false)
        }
        _uiState.value = _uiState.value.copy(text = text)
    }

    private suspend fun getSuggestions(
        text: String,
        type: KClass<out Place>,
        languageCode: String
    ): Result<List<Place>> {
        return when (type) {
            City::class -> helper.getCitySuggestions(text, languageCode)

            Country::class -> helper.getCountrySuggestions(text, languageCode)

            Address::class -> helper.getAddressSuggestions(search = text, languageCode)

            else -> {
                throw IllegalArgumentException("Unsupported place type")
            }
        }
    }

    fun onSuggestionSelected(suggestion: String) {
        _uiState.value = _uiState.value.copy(text = suggestion, isSuggestionsPopupExpanded = false)
    }

    fun onSuggestionPopupDismissRequested() {
        _uiState.value = _uiState.value.copy(isSuggestionsPopupExpanded = false)
    }
}