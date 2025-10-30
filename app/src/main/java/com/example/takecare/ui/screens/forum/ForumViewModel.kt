package com.example.takecare.ui.screens.forum

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Insert.PostModelCreate
import com.example.takecare.data.models.Post
import com.example.takecare.ui.Utils.SessionManager
import com.example.takecare.ui.Utils.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class ForumViewModel : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UIEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _openPost = MutableStateFlow<Post?>(null)
    val openPost : StateFlow<Post?> = _openPost

    private val _createdPost = MutableStateFlow<Post?>(null)
    val createdPost : StateFlow<Post?> = _createdPost

    private val _createdComment = MutableStateFlow<Comment?>(null)
    val createdComment : StateFlow<Comment?> = _createdComment

    init {
        getAllPost()
    }

    fun getAllPost() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.getAllPost()
                if (response.isSuccessful) {
                    val postResponse = response.body() ?: emptyList()
                    _posts.value = postResponse
                } else {
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al obtener los post " + response.code()))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor " + e.message))
            }
        }
    }

    fun openPostSelected(id: Int) {
        Log.i("POSTINFO", "Se abrio el post")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.getAPost(id)
                if (response.isSuccessful) {
                    Log.i("POSTGET", response.body().toString())
                    val postResponse = response.body()
                    _openPost.value = postResponse
                } else {
                    Log.e("POSTGET", response.body().toString())
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al abrir el post " + response.code()))
                }
            } catch (e: Exception) {
                Log.i("POSTGET", e.message.toString())
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor"))
            }
        }
    }

    fun addPost(post: PostModelCreate) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.addPost(post)
                if (response.isSuccessful) {
                    Log.i(
                        "POSTCREATION",
                        "Código: ${response.code()} | Cuerpo: ${response.body()} | Mensaje: ${response.message()}"
                    )
                    _uiEvent.emit(UIEvent.Showsnackbar("Se creó el post con éxito"))
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "POSTCREATION_ERROR",
                        "Error ${response.code()} | Mensaje: ${response.message()} | Cuerpo de error: $errorBody"
                    )
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al agregar el post: ${response.code()}"))
                }
            } catch (e: Exception) {
                Log.e("POSTCREATION_EXCEPTION", "Excepción: ${e.message}", e)
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor: ${e.message}"))
            }
        }
    }

    fun responsePost(id: Int, comment: Comment) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.responseAPost(id, comment)
                if (response.isSuccessful) {
                    val postResponse = response.body()
                } else {
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al responder el post " + response.code()))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor"))
            }
        }
    }
}