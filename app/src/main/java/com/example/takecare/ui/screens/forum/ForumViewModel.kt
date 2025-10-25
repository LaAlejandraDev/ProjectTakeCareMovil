package com.example.takecare.ui.screens.forum

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.takecare.data.exampleData.sampleComments
import com.example.takecare.data.exampleData.samplePosts
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ForumViewModel : ViewModel() {
    private val _posts = mutableStateOf<List<Post?>>(emptyList())
    val posts: State<List<Post?>> = _posts

    private val _openPost = MutableStateFlow<Post?>(null)
    val openPost : StateFlow<Post?> = _openPost

    private val _createdPost = MutableStateFlow<Post?>(null)
    val createdPost : StateFlow<Post?> = _createdPost

    private val _createdComment = MutableStateFlow<Comment?>(null)
    val createdComment : StateFlow<Comment?> = _createdComment

    val comments = mutableStateListOf<Comment>()

    init {
        loadSamplePosts()
        comments.addAll(sampleComments.filterNotNull())
    }

    fun incrementLikes(id: Int) {
        val index = comments.indexOfFirst { it.id == id }
        if (index != -1) {
            val comment = comments[index]
            comments[index] = comment.copy(likes = comment.likes + 1)
        }
    }

    fun asignNewComment(comment: Comment) {
        _createdComment.value = comment
    }

    fun createComment(comment: Comment) {
        if (comment.comprobateComment()) {
            _createdComment.value = comment
            sampleComments.add(createdComment.value)
            Log.i("FORUMPOST", "Se agrego un nuevo comentario")
        } else {
            Log.e("FORUMPOST", "Error al agregar un comentario")
        }
    }

    fun asingNewPost(post: Post) {
        _createdPost.value = post
    }

    fun createPost(post: Post) {
        if (post.comprobatePost()) {
            _createdPost.value = post
            samplePosts.add(createdPost.value)
            Log.i("FORUMPOST", "Se agrego un nuevo elemento")
        } else {
            Log.e("FORUMPOST", "Error al agregar el post")
        }
    }

    fun selectPost(post: Post) {
        Log.i("POSTCARD", post.id.toString())
        _openPost.value = post
    }

    private fun loadSamplePosts() {
        _posts.value = samplePosts
    }
}