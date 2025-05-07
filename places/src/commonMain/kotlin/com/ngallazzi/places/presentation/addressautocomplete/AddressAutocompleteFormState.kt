package com.ngallazzi.places.presentation.addressautocomplete

data class AddressAutocompleteFormState(
    val address: String = "",
    val city: String = "",
    val country: String = "",
    val postalCode: String = "",
)

fun AddressAutocompleteFormState.isValid(): Boolean {
    return address.isNotEmpty() && city.isNotEmpty() && country.isNotEmpty() && postalCode.isNotEmpty()
}