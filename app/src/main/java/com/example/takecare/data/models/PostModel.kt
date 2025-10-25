package com.example.takecare.data.models

data class Post (
    val id: Int,
    val title: String,
    val content: String,
    val date: String,
    val type: PostType,
    val user: User,
    val likesCount: Int = 0,
    val commentCount: Int = 0,
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