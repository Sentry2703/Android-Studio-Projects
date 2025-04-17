package com.example.simpleserverapp.core.network

import com.example.simpleserverapp.core.model.FileTypes
import com.example.simpleserverapp.core.model.ListResponse
import com.example.simpleserverapp.core.model.TypedFile
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.http.GET
import retrofit2.http.Path

fun createRetrofit(baseIP: String): FileApiService {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl("http://${baseIP}:12000")
        .build()

    return retrofit.create(FileApiService::class.java)
}

interface FileApiService {
    @GET("files/types")
    suspend fun getFileTypes() : FileTypes

    @GET("files/{file_type}")
    suspend fun getFilesOfType(@Path("file_type") fileType: String) : ListResponse
}
