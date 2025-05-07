package com.ngallazzi.places.domain

internal data class Address(override val id: String, val value: String, val extendedValue: String) :
    Place(id = id, label = value, extendedLabel = extendedValue)