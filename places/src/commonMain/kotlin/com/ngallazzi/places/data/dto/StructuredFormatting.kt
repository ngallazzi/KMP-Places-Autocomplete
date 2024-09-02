package com.ngallazzi.places.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StructuredFormatting(@SerialName("main_text") val mainText: String)
