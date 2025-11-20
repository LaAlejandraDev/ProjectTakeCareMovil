package com.example.takecare.data.models.Insert

import com.google.gson.annotations.SerializedName

data class DateModelCreate(
    @SerializedName("id")
    val id: Int,

    @SerializedName("idPsicologo")
    val psycologistId: Int,

    @SerializedName("idPaciente")
    val patientId: Int,

    @SerializedName("idDisponibilidad")
    val workDayId: Int,

    @SerializedName("fechaInicio")
    val startDate: String,

    @SerializedName("fechaFin")
    val endDate: String,

    @SerializedName("estado")
    val status: String,

    @SerializedName("motivo")
    val reason: String,

    @SerializedName("ubicacion")
    val location: String
)