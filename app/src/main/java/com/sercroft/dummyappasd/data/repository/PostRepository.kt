package com.sercroft.dummyappasd.data.repository

import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val apiService: JsonPlaceholderApiService
) {
    suspend fun getPosts(): List<Post> {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error to get post: ${response.message()}")
        }
    }
}
