package com.walmart.app.network

import com.walmart.app.model.Country
import retrofit2.HttpException

class CountryNetworkRepository(
    private val countryService: CountryService
) : CountryRepository {

    override suspend fun getCountries(): RequestResponse<List<Country>> {
        val response = countryService.getCountryList()
        return if (response.isSuccessful) {
            val countryList = response.body()?.map { country ->
                // The flag image URL given in the json file seems to be broken, so I'm mapping
                // the urls to use a different cdn
                country.copy(
                    flag = "https://flagcdn.com/144x108/${country.code.lowercase()}.png"
                )
            } ?: emptyList()
            RequestResponse.Success(countryList)
        } else {
            RequestResponse.Error(HttpException(response))
        }
    }
}
