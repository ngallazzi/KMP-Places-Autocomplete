package com.ngallazzi.places.domain

internal data class Country(override val id: String, val name: String, val extendedName: String) :
    Place(id = id, label = name, extendedLabel = extendedName)