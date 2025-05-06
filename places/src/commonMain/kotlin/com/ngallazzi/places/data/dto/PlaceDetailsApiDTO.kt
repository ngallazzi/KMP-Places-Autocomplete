package com.ngallazzi.places.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlaceDetailsApiDTO(
    val result: DTOResult,
    val status: String,
)

@Serializable
internal data class DTOResult(
    @SerialName("place_id") val placeId: String,
    @SerialName("address_components") val addressComponents: List<AddressComponents>,
    @SerialName("formatted_address") val formattedAddress: String,
    @SerialName("name") val name: String
)