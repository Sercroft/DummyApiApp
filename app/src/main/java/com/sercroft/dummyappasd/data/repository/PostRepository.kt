package com.sercroft.dummyappasd.data.repository

import com.sercroft.dummyappasd.data.dao.UserDao
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.model.User
import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PostRepository @Inject constructor(
    private val apiService: JsonPlaceholderApiService,
    private val userDao: UserDao
) {
    suspend fun getPosts(): List<Post> {
        val response = apiService.getPosts()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error to get post: ${response.message()}")
        }
    }

    suspend fun insertUsers(users: List<User>) {
        userDao.insertAll(users)
    }

    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}