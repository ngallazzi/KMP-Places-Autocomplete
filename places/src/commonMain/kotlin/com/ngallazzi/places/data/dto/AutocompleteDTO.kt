package com.ngallazzi.places.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AutocompleteDTO(val predictions: List<Prediction>, val status: String)