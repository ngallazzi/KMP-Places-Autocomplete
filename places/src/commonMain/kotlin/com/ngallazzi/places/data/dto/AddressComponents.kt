package com.ngallazzi.places.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressComponents(
    @SerialName("long_name") val longName: String,
    @SerialName("short_name") val shortName: String,
    val types: List<String>
)