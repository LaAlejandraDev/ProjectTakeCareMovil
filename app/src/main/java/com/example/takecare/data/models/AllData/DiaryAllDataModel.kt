package com.example.takecare.data.models.AllData

import com.google.gson.annotations.SerializedName

data class DiaryAllDataModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("idPaciente")
    val patientId: Int,
    @SerializedName("fecha")
    val date: String,
    @SerializedName("estadoEmocional")
    val emocionalState: String,
    @SerializedName("nivel")
    val level: Int,
    @SerializedName("comentario")
    val comment: String
)