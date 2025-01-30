package com.ngallazzi.places.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
    private val _uiState =
        MutableStateFlow(PlaceAutocompleteState(textFieldValue = TextFieldValue(text = initialText)))
    val uiState: StateFlow<PlaceAutocompleteState> = _uiState.asStateFlow()

    fun onValueChange(value: TextFieldValue) {
        if (value.text.isNotEmpty() && value.text != _uiState.value.textFieldValue.text) {
            viewModelScope.launch {
                getSuggestions(
                    value.text,
                    type = placeType,
                    languageCode = languageCode
                ).fold(onSuccess = { places ->
                    _uiState.value = _uiState.value.copy(
                        suggestions = places.map { if (isExtendedModeActive) it.extendedLabel else it.label }
                            .distinct(),
                        isSuggestionsPopupExpanded = places.isNotEmpty(),
                        errorText = null
                    )
                }, onFailure = {
                    _uiState.value = _uiState.value.copy(errorText = it.message.orEmpty())
                })
            }
        } else {
            _uiState.value =
                _uiState.value.copy(suggestions = listOf(), isSuggestionsPopupExpanded = false)
        }
        _uiState.value = _uiState.value.copy(textFieldValue = value)
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
        _uiState.value = _uiState.value.copy(
            textFieldValue = _uiState.value.textFieldValue.copy(
                text = suggestion, selection = TextRange(suggestion.length)
            ),
            isSuggestionsPopupExpanded = false,
            errorText = null
        )
    }

    fun onSuggestionPopupDismissRequested() {
        _uiState.value = _uiState.value.copy(isSuggestionsPopupExpanded = false)
    }
}