package com.sercroft.dummyappasd.data.service

import com.sercroft.dummyappasd.data.model.Post
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholderApiService{
    @GET("users")
    suspend fun getPosts(): Response<List<Post>>
}
