package com.ngallazzi.places.domain

data class City(val id: String, val name: String, val extendedName: String) :
    Place(label = name, extendedLabel = extendedName)