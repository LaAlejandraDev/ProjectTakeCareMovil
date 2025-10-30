package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class Post(
    @SerializedName("id") val id: Int = 0,

    @SerializedName("titulo") val title: String,

    @SerializedName("contenido") val content: String,

    @SerializedName("fecha") val date: String,

    @SerializedName("tipo") val type: Int,

    @SerializedName("idUsuario") val userId: Int,

    @SerializedName("usuario") val user: User,

    @SerializedName("anonimo") val isAnonymous: Boolean = false,

    @SerializedName("likesCount") val likesCount: Int = 0,

    @SerializedName("commentCount") val commentCount: Int = 0
) {
    fun comprobatePost() : Boolean {
        if (this.title.length < 25) return false
        if (this.content.length < 50) return false
        return true
    }

    fun getPostTypeText(type: Int = this.type): String {
        return when (type) {
            PostType.QUESTION.ordinal -> PostType.QUESTION.displayName
            PostType.DISCUSSION.ordinal -> PostType.DISCUSSION.displayName
            PostType.ANNOUNCEMENT.ordinal -> PostType.ANNOUNCEMENT.displayName
            PostType.GUIDE.ordinal -> PostType.GUIDE.displayName
            PostType.FEEDBACK.ordinal -> PostType.FEEDBACK.displayName
            else -> "Desconocido"
        }
    }

}

enum class PostType(val displayName: String) {
    QUESTION("Pregunta"),
    DISCUSSION("Discusión"),
    ANNOUNCEMENT("Anuncio"),
    GUIDE("Guía"),
    FEEDBACK("Retroalimentación")
}