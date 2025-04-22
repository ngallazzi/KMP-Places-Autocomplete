package com.ngallazzi.places.domain

interface PlaceDetailsInteractor {
    suspend fun getPlaceDetails(placeId: String, languageCode: String): Result<PlaceDetails>
}