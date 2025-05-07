package com.ngallazzi.places.domain

internal interface PlaceDetailsInteractor {
    suspend fun getPlaceDetails(placeId: String, languageCode: String): Result<PlaceDetails>
}