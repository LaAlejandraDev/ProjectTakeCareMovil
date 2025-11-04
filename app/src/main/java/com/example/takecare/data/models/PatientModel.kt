package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class PatientModel(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("idUsuario")
    val userId: Int?,

    @SerializedName("ciudad")
    val city: String?,

    @SerializedName("estadoCivil")
    val maritalStatus: String?,

    @SerializedName("diagnostico")
    val diagnostic: String?,

    @SerializedName("antecedentesMedicos")
    val medicalBackground: String?,

    @SerializedName("contactoEmergencia")
    val emergencyContact: String?,

    @SerializedName("usuario")
    val user: User?
)
