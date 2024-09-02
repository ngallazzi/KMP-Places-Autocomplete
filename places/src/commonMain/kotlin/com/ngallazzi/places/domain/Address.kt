package com.ngallazzi.places.domain

data class Address(val id: String, val value: String) : Place(label = value)