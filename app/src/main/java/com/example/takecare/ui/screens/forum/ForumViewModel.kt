package com.example.takecare.ui.screens.forum

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.takecare.data.exampleData.samplePosts
import com.example.takecare.data.models.Post

class ForumViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<Post>>(emptyList())
    val posts: State<List<Post>> = _posts

    init {
        loadSamplePosts()
    }

    private fun loadSamplePosts() {
        _posts.value = samplePosts
    }
}