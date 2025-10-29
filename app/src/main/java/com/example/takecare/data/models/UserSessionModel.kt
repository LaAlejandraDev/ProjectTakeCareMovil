package com.example.takecare.data.models

data class UserSession(
    val token: String = "",
    val userId: Int = -1,
    val userName: String = "",
    val email: String = "",
    val role: Int = 0
)