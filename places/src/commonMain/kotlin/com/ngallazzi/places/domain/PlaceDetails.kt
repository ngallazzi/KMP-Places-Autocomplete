package com.ngallazzi.places.domain

data class PlaceDetails(
    val id: String,
    val shortAddress: String,
    val formattedAddress: String,
    val postalCode: String,
    val country: String,
    val city: String,
) {
    override fun toString(): String {
        return "PlaceDetails(id='$id', shortAddress='$shortAddress', formattedAddress='$formattedAddress', postalCode='$postalCode', country='$country', city='$city')"
    }
}