package com.ngallazzi.places.presentation

import com.ngallazzi.places.data.PlacesRemoteDataSource
import com.ngallazzi.places.data.SuggestionsInteractorImpl
import com.ngallazzi.places.domain.Address
import com.ngallazzi.places.domain.City
import com.ngallazzi.places.domain.Country
import com.ngallazzi.places.domain.SuggestionsInteractor
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "maps.googleapis.com"

class PlacesHelper(private val apiKey: String) : SuggestionsInteractor {
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

    private val interactor: SuggestionsInteractor = SuggestionsInteractorImpl(
        PlacesRemoteDataSource(
            httpClient
        )
    )

    override suspend fun getCountrySuggestions(
        search: String,
        languageCode: String
    ): Result<List<Country>> {
        return interactor.getCountrySuggestions(search, languageCode)
    }

    override suspend fun getCitySuggestions(
        search: String,
        languageCode: String
    ): Result<List<City>> {
        return interactor.getCitySuggestions(search, languageCode)
    }

    override suspend fun getAddressSuggestions(
        search: String,
        languageCode: String
    ): Result<List<Address>> {
        return interactor.getAddressSuggestions(search, languageCode)
    }
}