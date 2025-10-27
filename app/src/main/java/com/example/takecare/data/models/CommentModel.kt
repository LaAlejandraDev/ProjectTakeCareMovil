package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName("Id")
    val id: Int,
    @SerializedName("IdPost")
    val postId: Int,
    @SerializedName("Fecha")
    val date: String,
    @SerializedName("Contenido")
    val content: String,
    @SerializedName("IdUsuario")
    val userId: Int,
    @SerializedName("Usuario")
    val user: User,
    @SerializedName("Likes")
    val likes: Int = 0,
    @SerializedName("Anonimo")
    val isAnimus: Boolean
) {
    fun comprobateComment() : Boolean {
        if (this.content.length < 25) return false
        return true
    }
}