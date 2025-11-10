package com.example.takecare.data.models.AllData

import com.google.gson.annotations.SerializedName

data class MessageAllDataModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("idChat")
    val chatId: Int,
    @SerializedName("idRemitenteUsuario")
    val senderId: Int,
    @SerializedName("mensaje")
    val message: String,
    @SerializedName("leido")
    val readed: Boolean,
    @SerializedName("fecha")
    val date: String
)