package com.example.takecare.data.models.Insert

import com.example.takecare.data.models.User
import com.google.gson.annotations.SerializedName

data class PostModelCreate(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("titulo") val title: String,
    @SerializedName("contenido") val content: String,
    @SerializedName("fecha") val date: String,
    @SerializedName("tipo") val type: Int,
    @SerializedName("idUsuario") val userId: Int,
    @SerializedName("anonimo") val isAnonymous: Boolean = false,
    @SerializedName("likesCount") val likesCount: Int = 0,
    @SerializedName("commentCount") val commentCount: Int = 0
)