package com.walmart.app.di

import com.walmart.app.network.CountryNetworkRepository
import com.walmart.app.network.CountryRepository
import com.walmart.app.network.CountryService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class NetworkRepository

@Module
@InstallIn(SingletonComponent::class)
class CountryRepositoryModule {

    // TODO in a real app, I'd probably move the base url to a build variable so we can change
    //  it in CI for different stages
    private val baseUrl = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/db25946fd77c5873b0303b858e861ce724e0dcd0/"

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesCountryService(
        retrofit: Retrofit
    ): CountryService {
        return retrofit.create(CountryService::class.java)
    }

    @Singleton
    @Provides
    @NetworkRepository
    fun providesCountryNetworkRepository(
        countryService: CountryService
    ): CountryRepository {
        return CountryNetworkRepository(
            countryService = countryService
        )
    }
}
