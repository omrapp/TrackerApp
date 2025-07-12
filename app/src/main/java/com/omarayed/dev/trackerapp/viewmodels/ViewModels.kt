package com.omarayed.dev.trackerapp.viewmodels

import TrackerSDK
import android.annotation.SuppressLint
import androidx.benchmark.perfetto.UiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarayed.dev.trackerapp.data.Post
import com.omarayed.dev.trackerapp.data.User
import com.omarayed.dev.trackerapp.network.NetworkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@SuppressLint("RestrictedApi")
class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(mutableListOf<User>())
    val uiState = _uiState.asStateFlow()


    init {
        TrackerSDK.start("fetching_users_list")
        fetchUsers()
        TrackerSDK.start("fetching_users_list")
    }

    private fun fetchUsers() {

        viewModelScope.launch {
            try {
                val data = NetworkManager.apiService.getUsers()
                _uiState.value = data.toMutableList()
            } catch (e: Exception) {
                // Handle errors

            }
        }
    }

}


@SuppressLint("RestrictedApi")
class PostViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(mutableListOf<Post>())
    val uiState = _uiState.asStateFlow()


    init {
        TrackerSDK.start("fetching_posts_list")
        fetchPosts()
        TrackerSDK.start("fetching_posts_list")
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            try {
                val data = NetworkManager.apiService.getPosts()
                _uiState.value = data.toMutableList()
            } catch (e: Exception) {
                // Handle errors

            }
        }
    }

}