package com.walmart.app.network

import retrofit2.HttpException

/**
 * Base response class to encapsulate the status of a network request. Successful requests should
 * return an instance of [Success] with the response in the data attribute.
 * Unsuccessful responses should return an instance of [Error] with the exception so it can be used
 * later if needed.
 */
sealed class RequestResponse<T> {

    data class Success<T>(val data: T) : RequestResponse<T>()

    data class Error<T>(val exception: HttpException) : RequestResponse<T>()
}
