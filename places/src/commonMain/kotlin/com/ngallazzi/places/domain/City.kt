package com.ngallazzi.places.domain

data class City(val id: String, val value: String) : Place(label = value)