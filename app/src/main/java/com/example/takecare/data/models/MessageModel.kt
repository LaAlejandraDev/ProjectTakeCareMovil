package com.example.takecare.data.models

data class Message (
    val id: Int,
    val fromUser: User,
    val toUser: User,
    val title: String,
    val content: String,
    val state: MessageState,
    val confirmed: Boolean,
    val date: String
)

enum class MessageState {
    SENT,
    DELIVERED,
    READ
}