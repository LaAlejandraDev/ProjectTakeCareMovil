package com.example.takecare.data.models.Insert

import com.google.gson.annotations.SerializedName

data class ChatModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("idPsicologo")
    val idPsychologist: Int,
    @SerializedName("idPaciente")
    val idPatient: Int,
    @SerializedName("creadoEn")
    val createdDate: String,
    @SerializedName("ultimoMensajeEn")
    val lastMessageDate: String,
)

data class CreateChat (
    @SerializedName("idPsicologo")
    val idPsychologist: Int,
    @SerializedName("idPaciente")
    val idPatient: Int,
)