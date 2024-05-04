package com.pinedeveloper.homework.domain

import com.pinedeveloper.homework.entity.RickAndMorty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickApiService {
    @GET("character")
    suspend fun getMarsPhotos(
        @Query("page") page: Int = 1,
    ): Response<RickAndMorty> // RESPONSE
}