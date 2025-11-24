package com.example.takecare.data.models.Insert

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
) {
    fun isStartDateExpired(): Boolean {
        return try {
            val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val start = LocalDateTime.parse(startDate, formatter)
            start.isBefore(LocalDateTime.now())
        } catch (e: Exception) {
            false
        }
    }
}