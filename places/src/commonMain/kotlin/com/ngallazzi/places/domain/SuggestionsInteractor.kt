package com.ngallazzi.places.domain

internal interface SuggestionsInteractor {
    suspend fun getCountrySuggestions(search: String, languageCode: String): Result<List<Country>>
    suspend fun getCitySuggestions(
        search: String,
        languageCode: String
    ): Result<List<City>>

    suspend fun getAddressSuggestions(
        search: String,
        languageCode: String
    ): Result<List<Address>>
}