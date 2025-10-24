package com.example.takecare.ui.screens.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.takecare.data.models.Post

class HomeViewModel : ViewModel() {
    private val _openPost = mutableStateOf<Post?>(null)
    val openPost : State<Post?> = _openPost

    fun selectPost(post: Post) {
        _openPost.value = post
    }
}