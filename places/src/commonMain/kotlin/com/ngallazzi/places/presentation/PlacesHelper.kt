package com.ngallazzi.places.presentation

import com.ngallazzi.places.data.PlaceDetailsInteractorImpl
import com.ngallazzi.places.data.PlacesRemoteDataSource
import com.ngallazzi.places.data.SuggestionsInteractorImpl
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.domain.PlaceDetails
import com.ngallazzi.places.domain.PlaceDetailsInteractor
import com.ngallazzi.places.domain.SuggestionsInteractor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "maps.googleapis.com"

internal class PlacesHelper(private val apiKey: String) : SuggestionsInteractor,
    PlaceDetailsInteractor {
    private val httpClient = HttpClient {
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BASE_URL
                parameters["key"] = apiKey
            }
        }
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private val suggestionsInteractor: SuggestionsInteractor = SuggestionsInteractorImpl(
        PlacesRemoteDataSource(
            httpClient
        )
    )

    private val detailsInteractor: PlaceDetailsInteractor = PlaceDetailsInteractorImpl(
        PlacesRemoteDataSource(
            httpClient
        )
    )

    override suspend fun getCountrySuggestions(
        search: String,
        languageCode: String
    ): Result<List<Country>> {
        return suggestionsInteractor.getCountrySuggestions(search, languageCode)
    }

    override suspend fun getCitySuggestions(
        search: String,
        languageCode: String
    ): Result<List<City>> {
        return suggestionsInteractor.getCitySuggestions(search, languageCode)
    }

    override suspend fun getAddressSuggestions(
        search: String,
        languageCode: String
    ): Result<List<Address>> {
        return suggestionsInteractor.getAddressSuggestions(search, languageCode)
    }

    override suspend fun getPlaceDetails(
        placeId: String,
        languageCode: String
    ): Result<PlaceDetails> {
        return detailsInteractor.getPlaceDetails(placeId, languageCode)
    }
}