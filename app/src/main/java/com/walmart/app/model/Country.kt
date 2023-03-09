package com.walmart.app.model

// Assumptions:
// For the purpose of this assignment, I am assuming that all fields are present (non-nullable)
data class Country(
    val capital: String,
    val code: String,
    val currency: CountryCurrency,
    val flag: String,
    val language: CountryLanguage,
    val name: String,
    val region: String
)
