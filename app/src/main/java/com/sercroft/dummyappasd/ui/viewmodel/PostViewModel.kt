package com.sercroft.dummyappasd.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercroft.dummyappasd.data.model.Post
import com.sercroft.dummyappasd.data.service.JsonPlaceholderApiService
import com.sercroft.dummyappasd.data.service.RandomDogApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val jsonPlaceholderApiService: JsonPlaceholderApiService
) : ViewModel() {

    val _posts = MutableLiveData<List<Post>>()
    private val _filteredPosts = MutableLiveData<List<Post>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val posts: LiveData<List<Post>>
        get() = _posts
    val filteredPosts: LiveData<List<Post>>
        get() = _filteredPosts
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = jsonPlaceholderApiService.getPosts()
                if (response.isSuccessful) {
                    _posts.value = response.body() ?: emptyList()
                    _filteredPosts.value = _posts.value
                } else {
                    handleError("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                handleError("Error: ${e.localizedMessage}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filterPosts(query: String) {
        _filteredPosts.value = _posts.value?.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }
}

private fun handleError(message: String) {
    println(message)
}


