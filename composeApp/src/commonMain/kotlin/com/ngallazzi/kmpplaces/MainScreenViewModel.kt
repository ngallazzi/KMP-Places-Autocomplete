package com.ngallazzi.kmpplaces

import androidx.lifecycle.ViewModel
import com.ngallazzi.places.domain.PlaceDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class MainScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainScreenState())
    val uiState: StateFlow<MainScreenState> = _uiState.asStateFlow()
    fun onAddressSelected(placeDetails: PlaceDetails) {
        _uiState.value = _uiState.value.copy(
            address = placeDetails.shortAddress,
            city = placeDetails.city,
            country = placeDetails.country,
            postalCode = placeDetails.postalCode
        )
    }

    fun onCitySelected(placeDetails: PlaceDetails) {
        _uiState.value = _uiState.value.copy(
            city = placeDetails.city
        )
    }

    fun onCountrySelected(placeDetails: PlaceDetails) {
        _uiState.value = _uiState.value.copy(
            country = placeDetails.country
        )
    }

    fun onAddressCleared() {
        _uiState.value = MainScreenState()
    }
}