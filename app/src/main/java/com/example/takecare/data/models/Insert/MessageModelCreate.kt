package com.example.takecare.data.models.Insert

import com.google.gson.annotations.SerializedName

data class MessageModelCreate (
    @SerializedName("idChat")
    val chatId: Int,

    @SerializedName("idRemitenteUsuario")
    val senderUserId: Int,

    @SerializedName("mensaje")
    val message: String,

    @SerializedName("leido")
    val isRead: Boolean?,
)