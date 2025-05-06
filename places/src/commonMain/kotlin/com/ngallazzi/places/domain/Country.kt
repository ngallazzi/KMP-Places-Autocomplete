package com.ngallazzi.places.domain

data class Country(override val id: String, val name: String, val extendedName: String) :
    Place(id = id, label = name, extendedLabel = extendedName)