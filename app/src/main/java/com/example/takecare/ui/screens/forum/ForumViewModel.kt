package com.example.takecare.ui.screens.forum

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.CreateComment
import com.example.takecare.data.models.Insert.PostModelCreate
import com.example.takecare.data.models.Post
import com.example.takecare.ui.Utils.SessionManager
import com.example.takecare.ui.Utils.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.firstOrNull
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

    private val _postCommentsList = MutableStateFlow<List<Comment?>>(emptyList())
    val postCommentList : StateFlow<List<Comment?>> = _postCommentsList

    private val _userId = MutableStateFlow(-1)
    val userId : StateFlow<Int> = _userId

    init {
        getAllPost()
    }

    fun getUserId(context: Context) {
        viewModelScope.launch {
            try {
                val sessionManager = SessionManager(context)
                val userId = sessionManager.getUserId().firstOrNull()
                if (userId != null) {
                    _userId.value = userId
                }
            } catch (e: Exception) {
            }
        }
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
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.getAPost(id)
                if (response.isSuccessful) {
                    val postResponse = response.body()
                    _openPost.value = postResponse
                } else {
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al abrir el post " + response.code()))
                }
            } catch (e: Exception) {
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor"))
            }
        }
    }

    fun addPost(post: PostModelCreate, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerForum.addPost(post)
                if (response.isSuccessful) {
                    _uiEvent.emit(UIEvent.Showsnackbar("Se creó el post con éxito"))
                    onResult(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al agregar el post: ${response.code()}"))
                    onResult(false)
                }
            } catch (e: Exception) {
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor: ${e.message}"))
                onResult(false)
            }
        }
    }

    fun getPostComments(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerComments.getPostCommets(id)

                if (response.isSuccessful) {
                    val commentsList = response.body() ?: emptyList()
                    _postCommentsList.value = commentsList

                    commentsList.forEachIndexed { index, comment ->
                        Log.d("GETCOMMENTS_ITEM", "[$index] id=${comment.id}, contenido=${comment.content}, usuario=${comment.userId}")
                    }

                } else {
                    val errorBody = response.errorBody()?.string()
                    _postCommentsList.value = emptyList()

                    Log.e(
                        "GETCOMMENTS_ERROR",
                        """
                    Código HTTP: ${response.code()}
                    Mensaje: ${response.message()}
                    Error body: $errorBody
                    """.trimIndent()
                    )
                }

            } catch (e: Exception) {
            } finally {
                Log.i("GETCOMMENTS", "Finalizó la solicitud de comentarios para el post id=$id")
            }
        }
    }

    fun responsePost(comment: CreateComment, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerComments.addComment(comment)

                if (response.isSuccessful) {
                    val postResponse = response.body()
                    Log.i("COMMENT_POST", "Comentario agregado correctamente: $postResponse")
                    _uiEvent.emit(UIEvent.Showsnackbar("Comentario agregado correctamente"))
                    onResult(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("COMMENT_POST", "Error ${response.code()} al enviar comentario: $errorBody")
                    _uiEvent.emit(UIEvent.Showsnackbar("Error al responder el post (${response.code()})"))
                    onResult(false)
                }

            } catch (e: Exception) {
                Log.e("COMMENT_POST", "Excepción al enviar comentario: ${e.message}", e)
                _uiEvent.emit(UIEvent.Showsnackbar("Error de servidor: ${e.localizedMessage ?: "desconocido"}"))
                onResult(false)
            }
        }
    }
}