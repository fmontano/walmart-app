package com.walmart.app.network

import com.walmart.app.model.Country

interface CountryRepository {
    suspend fun getCountries(): RequestResponse<List<Country>>
}
