package com.ngallazzi.places.domain

data class Address(val id: String, val value: String, val extendedValue: String) :
    Place(label = value, extendedLabel = extendedValue)