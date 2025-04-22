package com.ngallazzi.places.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceDetailsApiDTO(
    val result: List<AddressComponents>,
    val status: String,
    @SerialName("formatted_address")
    val formattedAddress: String
)