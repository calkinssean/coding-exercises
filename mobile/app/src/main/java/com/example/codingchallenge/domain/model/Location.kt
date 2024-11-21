package com.example.codingchallenge.domain.model

data class Location(
    val attributes: List<Attribute>,
    val id: Int,
    val latitude: Double,
    val longitude: Double
)