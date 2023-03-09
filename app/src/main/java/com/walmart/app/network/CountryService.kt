package com.walmart.app.network

import com.walmart.app.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {

    @GET("countries.json")
    suspend fun getCountryList(): Response<List<Country>>
}
