package com.example.takecare.data.models.Insert
import com.google.gson.annotations.SerializedName

data class DiaryInsertModel(
    @SerializedName("idPaciente")
    val patientId: Int,
    @SerializedName("nivel")
    val level: Int,
    @SerializedName("estadoEmocional")
    val emotionalStatus: String,
    @SerializedName("comentario")
    val comment: String
)