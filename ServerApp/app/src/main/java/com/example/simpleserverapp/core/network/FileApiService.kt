package com.example.simpleserverapp.core.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

fun createRetrofit(baseIP: String): FileApiService {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl("http://${baseIP}:12000")
        .build()

    return retrofit.create(FileApiService::class.java)
}

interface FileApiService {
    @GET("files/types")
    suspend fun getFileTypes() : String
}
