package com.example.takecare.data.models

data class Comment (
    val id: Int,
    val postId: Int,
    val date: String,
    val content: String,
    val user: User,
    val likes: Int = 0
)