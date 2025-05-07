package com.ngallazzi.places.presentation.addressautocomplete

import androidx.lifecycle.ViewModel
import com.ngallazzi.places.domain.PlaceDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


internal class AddressAutocompleteFormViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddressAutocompleteFormState())
    val uiState: StateFlow<AddressAutocompleteFormState> = _uiState.asStateFlow()
    fun onAddressSelected(placeDetails: PlaceDetails) {
        _uiState.value = _uiState.value.copy(
            address = placeDetails.shortAddress,
            city = placeDetails.city,
            country = placeDetails.country,
            postalCode = placeDetails.postalCode
        )
    }

    fun onAddressCleared() {
        _uiState.value = AddressAutocompleteFormState()
    }

    fun onPostalCodeChanged(postalCode: String) {
        _uiState.value = _uiState.value.copy(postalCode = postalCode)
    }
}