package com.ngallazzi.places

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName(name = "KMPPlaces", exact = true)
object KMPPlaces {
    private var apiKey: String? = null
    fun initialize(apiKey: String) {
        this.apiKey = apiKey
    }

    internal fun getApiKey(): String {
        return apiKey ?: error("MyLibrary must be initialized with an API key.")
    }
}