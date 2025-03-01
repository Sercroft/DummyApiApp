package com.sercroft.dummyappasd.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.model.User
import com.sercroft.dummyappasd.data.repository.PostRepository
import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//@HiltViewModel
//class PostViewModel @Inject constructor(
//    private val jsonPlaceholderApiService: JsonPlaceholderApiService
//) : ViewModel() {
//
//    val _posts = MutableLiveData<List<Post>>()
//    private val _filteredPosts = MutableLiveData<List<Post>>()
//    private val _isLoading = MutableLiveData<Boolean>()
//
//    val posts: LiveData<List<Post>>
//        get() = _posts
//    val filteredPosts: LiveData<List<Post>>
//        get() = _filteredPosts
//    val isLoading: LiveData<Boolean>
//        get() = _isLoading
//
//    fun fetchPosts() {
//        viewModelScope.launch {
//            _isLoading.value = true
//            try {
//                val response = jsonPlaceholderApiService.getPosts()
//
//                Log.d("TAG-1", "RESPONSE: ${response.body()}")
//
//                if (response.isSuccessful) {
//                    _posts.value = response.body() ?: emptyList()
//                    _filteredPosts.value = _posts.value
//                } else {
//                    handleError("Error: ${response.code()}")
//                }
//            } catch (e: Exception) {
//                handleError("Error: ${e.localizedMessage}")
//            } finally {
//                _isLoading.value = false
//            }
//        }
//    }
//
//    fun filterPosts(query: String) {
//        _filteredPosts.value = _posts.value?.filter {
//            it.name.contains(query, ignoreCase = true)
//        }
//    }
//}
//
//private fun handleError(message: String) {
//    println(message)
//}
//

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts

    private val _filteredPosts = MutableLiveData<List<Post>>()
    val filteredPosts: LiveData<List<Post>> get() = _filteredPosts

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = repository.getPosts()
                _posts.value = response
                _filteredPosts.value = response

                val usersResponse = response.map {
                    User(it.id, it.name, it.userName, it.email, it.phone, it.website)
                }
                repository.insertUsers(usersResponse)
                _users.value = repository.getAllUsers()
            } catch (e: Exception) {
                handleError("Error: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterPosts(query: String) {
        _filteredPosts.value = _posts.value?.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

    private fun handleError(message: String) {
        println(message)
    }
}

