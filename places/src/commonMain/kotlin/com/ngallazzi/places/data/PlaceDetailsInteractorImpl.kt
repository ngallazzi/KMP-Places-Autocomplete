package com.ngallazzi.places.data

import com.ngallazzi.places.domain.PlaceDetails
import com.ngallazzi.places.domain.PlaceDetailsInteractor
import com.ngallazzi.places.domain.PlacesDataSource

internal class PlaceDetailsInteractorImpl(
    private val placesDataSource: PlacesDataSource,
) : PlaceDetailsInteractor {
    override suspend fun getPlaceDetails(
        placeId: String, languageCode: String
    ): Result<PlaceDetails> {
        return runCatching {
            val response =
                placesDataSource.getPlaceDetails(placeId = placeId, languageCode).getOrThrow()
            val formattedAddress = response.formattedAddress
            val postalCode =
                response.result.firstOrNull { it.types.contains("postal_code") }?.shortName ?: ""
            val countryName =
                response.result.firstOrNull { it.types.contains("country") }?.longName ?: ""
            val cityName =
                response.result.firstOrNull { it.types.contains("administrative_area_level_3") }?.longName
                    ?: ""
            return Result.success(PlaceDetails(formattedAddress, postalCode, countryName, cityName))
        }
    }
}