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
)

enum class PostType(val displayName: String) {
    QUESTION("Pregunta"),
    DISCUSSION("Discusión"),
    ANNOUNCEMENT("Anuncio"),
    GUIDE("Guía"),
    FEEDBACK("Retroalimentación")
}