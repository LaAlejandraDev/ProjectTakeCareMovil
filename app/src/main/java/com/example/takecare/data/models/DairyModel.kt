package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class DairyModel(
    @SerializedName("Id")
    val id: Int,
    @SerializedName("PacienteId")
    val patientId: Int,
    @SerializedName("Paciente")
    val patient: PatientModel,
    @SerializedName("Fecha")
    val date: String,
    @SerializedName("NivelAnimo")
    val level: Int,
    @SerializedName("PalabrasClave")
    val keywords: String?,
    @SerializedName("Narrativa")
    val narrative: String?,
    @SerializedName("RiesgoEmocional")
    val risk: Double,
    @SerializedName("AlertaEnviada")
    val isAlerted: Boolean
)