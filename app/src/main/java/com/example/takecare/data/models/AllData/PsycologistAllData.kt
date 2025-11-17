package com.example.takecare.data.models.AllData

import com.google.gson.annotations.SerializedName

data class PsycologistAllData (
    @SerializedName("id")
    val id: Int,

    @SerializedName("idUsuario")
    val userId: Int,

    @SerializedName("cedulaProfesional")
    val profesionalInfo: String,

    @SerializedName("especialidad")
    val speciality: String,

    @SerializedName("descripcion")
    val description: String,

    @SerializedName("experienciaAnios")
    val experience: Int,

    @SerializedName("universidadEgreso")
    val university: String,

    @SerializedName("direccionConsultorio")
    val direction: String,

    @SerializedName("calificacionPromedio")
    val rating: Float,

    @SerializedName("totalResenas")
    val totalReviews: Int
)