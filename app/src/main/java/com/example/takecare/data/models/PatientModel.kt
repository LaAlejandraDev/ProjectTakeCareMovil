package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class PatientModel (
    @SerializedName("Id")
    val id: Int?,
    @SerializedName("UsuarioId")
    val userId: Int?,
    @SerializedName("Ciudad")
    val city: String?,
    @SerializedName("genero")
    val gender: String,
    @SerializedName("fechaNacimiento")
    val bornDate: String,
    @SerializedName("EstadoCivil")
    val martialStatus: String?,
    @SerializedName("Diagnostico")
    val diagnostic: String?,
    @SerializedName("AntecedentesMedicos")
    val medicalBackground: String?,
    @SerializedName("ContactoEmergencia")
    val emergencyContact: String?,
    @SerializedName("Usuario")
    val user: User?
)