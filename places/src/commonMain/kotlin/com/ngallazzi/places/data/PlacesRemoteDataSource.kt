package com.ngallazzi.places.data

import com.ngallazzi.places.data.dto.AutocompleteDTO
import com.ngallazzi.places.domain.PlacesDataSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

internal class PlacesRemoteDataSource(
    private val httpClient: HttpClient
) :
    PlacesDataSource {
    override suspend fun searchCity(name: String, languageCode: String): Result<AutocompleteDTO> {
        return handleNetworkCall("maps/api/place/autocomplete/json?input=$name&types=(cities)&language=$languageCode")
    }

    override suspend fun searchCountry(
        name: String,
        languageCode: String
    ): Result<AutocompleteDTO> {
        return handleNetworkCall("maps/api/place/autocomplete/json?input=$name&type=country&language=$languageCode")
    }

    override suspend fun searchAddress(
        address: String,
        languageCode: String
    ): Result<AutocompleteDTO> {
        return handleNetworkCall("maps/api/place/autocomplete/json?input=$address&type=address&language=$languageCode")
    }

    private suspend fun handleNetworkCall(url: String): Result<AutocompleteDTO> {
        return try {
            val result = httpClient.get(url) {
                contentType(ContentType.Application.Json)
            }
            return when (result.status) {
                HttpStatusCode.OK -> {
                    val response = result.body<AutocompleteDTO>()
                    when (response.status) {
                        "OK" -> Result.success(response)
                        "REQUEST_DENIED" -> Result.failure(Exception("Invalid api key"))
                        else -> Result.failure(Exception(response.status))
                    }
                }

                else -> Result.failure(Exception(Exception(result.toString())))
            }

        } catch (e: Exception) {
            Result.failure(Throwable(e))
        }
    }
}