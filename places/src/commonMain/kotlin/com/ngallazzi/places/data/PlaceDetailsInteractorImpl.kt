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
            val formattedAddress = response.result.formattedAddress
            val postalCode =
                response.result.addressComponents.firstOrNull { it.types.contains("postal_code") }?.shortName
                    ?: ""
            val countryName =
                response.result.addressComponents.firstOrNull { it.types.contains("country") }?.longName
                    ?: ""
            val cityName =
                response.result.addressComponents.firstOrNull { it.types.contains("locality") || it.types.contains("postal_town") }?.longName
                    ?: ""
            return Result.success(
                PlaceDetails(
                    id = response.result.placeId,
                    formattedAddress = formattedAddress,
                    postalCode = postalCode,
                    country = countryName,
                    city = cityName,
                    shortAddress = response.result.name
                )
            )
        }
    }
}