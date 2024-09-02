package com.ngallazzi.places.domain

data class Country(val id: String, val name: String) : Place(label = name)