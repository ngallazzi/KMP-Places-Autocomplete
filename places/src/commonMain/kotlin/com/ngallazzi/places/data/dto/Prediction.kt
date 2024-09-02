package com.ngallazzi.places.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Prediction(
    val description: String,
    @SerialName("place_id") val placeId: String,
    @SerialName("structured_formatting") val structuredFormatting: StructuredFormatting,
)