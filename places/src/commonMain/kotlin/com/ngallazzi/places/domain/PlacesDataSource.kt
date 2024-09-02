package com.ngallazzi.places.domain

import com.ngallazzi.places.data.dto.AutocompleteDTO

internal interface PlacesDataSource {
    suspend fun searchCity(name: String, languageCode: String): Result<AutocompleteDTO>

    suspend fun searchCountry(name: String, languageCode: String): Result<AutocompleteDTO>

    suspend fun searchAddress(
        address: String,
        languageCode: String
    ): Result<AutocompleteDTO>
}