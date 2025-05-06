package com.ngallazzi.places.domain

internal data class PlaceDetails(
    val id: String,
    val shortAddress: String,
    val formattedAddress: String,
    val postalCode: String,
    val country: String,
    val city: String,
)