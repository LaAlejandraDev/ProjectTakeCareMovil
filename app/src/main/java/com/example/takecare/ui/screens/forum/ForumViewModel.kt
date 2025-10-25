package com.example.takecare.ui.screens.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.takecare.data.exampleData.samplePosts
import com.example.takecare.data.models.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ForumViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    private val _openPost = MutableStateFlow<Post?>(null)
    val openPost : StateFlow<Post?> = _openPost

    init {
        loadSamplePosts()
    }

    fun selectPost(post: Post) {
        _openPost.value = post
    }

    private fun loadSamplePosts() {
        _posts.value = samplePosts
    }
}