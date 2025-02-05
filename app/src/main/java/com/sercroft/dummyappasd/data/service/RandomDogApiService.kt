package com.sercroft.dummyappasd.data.service

import com.sercroft.dummyappasd.data.model.DoggysResponse
import retrofit2.http.GET

interface RandomDogApiService {
    @GET("woof.json")
    suspend fun getRandomDog(): DoggysResponse
}