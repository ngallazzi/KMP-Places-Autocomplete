package com.ngallazzi.places.domain

data class PlaceDetails(
    val formattedAddress: String,
    val postalCode: String,
    val countryName: String,
    val cityName: String,
)