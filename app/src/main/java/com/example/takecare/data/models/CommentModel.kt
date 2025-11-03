package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName("id")
    val id: Int?,

    @SerializedName("idPost")
    val postId: Int,

    @SerializedName("fecha")
    val date: String?,

    @SerializedName("contenido")
    val content: String?,

    @SerializedName("idUsuario")
    val userId: Int,

    @SerializedName("usuario")
    val user: User?,

    @SerializedName("likes")
    val likes: Int?,

    @SerializedName("anonimo")
    val isAnimus: Boolean?
)
data class CreateComment(
    @SerializedName("IdPost")
    val postId: Int,
    @SerializedName("Contenido")
    val content: String,
    @SerializedName("IdUsuario")
    val userId: Int,
) {
    fun comprobateComment() : Boolean {
        if (this.content.length < 25) return false
        return true
    }
}