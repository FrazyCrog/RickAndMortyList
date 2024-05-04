package com.pinedeveloper.homework.data

import com.pinedeveloper.homework.domain.RickApiService
import com.pinedeveloper.homework.entity.Info
import com.pinedeveloper.homework.entity.RickAndMorty
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RickRepository @Inject constructor() {
    private val BASE_URL = "https://rickandmortyapi.com/api/"

    suspend fun getListRickAndMorty(page:Int): RickAndMorty {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(RickApiService::class.java)

        val components = apiService.getMarsPhotos(page)
        return components.body() ?: RickAndMorty(Info(0,"",0,0), emptyList())
    }
}