package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class Post (
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Titulo")
    val title: String,
    @SerializedName("Contenido")
    val content: String,
    @SerializedName("Fecha")
    val date: String,
    @SerializedName("Tipo")
    val type: PostType,
    @SerializedName("IdUsuario")
    val userId: Int,
    @SerializedName("Usuario")
    val user: User,
    @SerializedName("Anonimo")
    val isAnimus: Boolean,
    @SerializedName("LikesCount")
    val likesCount: Int = 0,
    @SerializedName("CommentCount")
    val commentCount: Int = 0,
    @SerializedName("Comentarios")
    val comments: List<Comment>
) {
    fun comprobatePost() : Boolean {
        if (this.title.length < 25) return false
        if (this.content.length < 50) return false
        return true
    }
}

enum class PostType(val displayName: String) {
    QUESTION("Pregunta"),
    DISCUSSION("Discusión"),
    ANNOUNCEMENT("Anuncio"),
    GUIDE("Guía"),
    FEEDBACK("Retroalimentación")
}