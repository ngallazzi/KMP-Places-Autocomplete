package com.ngallazzi.places.presentation

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.domain.Place
import com.ngallazzi.places.domain.PlaceDetails
import com.ngallazzi.places.domain.Suggestion
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
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(
            PlaceAutocompleteState(
                textFieldValue = TextFieldValue(
                    text = initialText,
                    selection = TextRange(initialText.length)
                )
            )
        )
    val uiState: StateFlow<PlaceAutocompleteState> = _uiState.asStateFlow()

    fun onValueChange(value: TextFieldValue) {
        if (value.text.isNotEmpty() && value.text != _uiState.value.textFieldValue.text) {
            viewModelScope.launch {
                getSuggestions(
                    value.text, type = placeType, languageCode = languageCode
                ).fold(onSuccess = { places ->
                    _uiState.value = _uiState.value.copy(
                        suggestions = places.map {
                            val placeId = it.id
                            Suggestion(
                                placeId = placeId,
                                description = it.extendedLabel,
                                shortDescription = it.label
                            )
                        }.distinct(),
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
        text: String, type: KClass<out Place>, languageCode: String
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

    suspend fun onSuggestionSelected(
        suggestion: Suggestion,
        onPlaceDetailsRetrieved: suspend (PlaceDetails) -> Unit
    ) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                textFieldValue = _uiState.value.textFieldValue.copy(
                    text = suggestion.description,
                    selection = TextRange(suggestion.description.length)
                ), isSuggestionsPopupExpanded = false, errorText = null
            )
        }
        helper.getPlaceDetails(suggestion.placeId, languageCode).fold(onSuccess = {
            val placeDetails = PlaceDetails(
                id = it.id,
                shortAddress = it.shortAddress,
                formattedAddress = it.formattedAddress,
                postalCode = it.postalCode,
                country = it.country,
                city = it.city
            )
            onPlaceDetailsRetrieved(placeDetails)
        }, onFailure = {
            _uiState.value = _uiState.value.copy(errorText = it.message.orEmpty())
        })
    }

    fun onSuggestionPopupDismissRequested() {
        _uiState.value = _uiState.value.copy(isSuggestionsPopupExpanded = false)
    }

    fun onClearText() {
        _uiState.value = _uiState.value.copy(textFieldValue = TextFieldValue(""))
    }
}