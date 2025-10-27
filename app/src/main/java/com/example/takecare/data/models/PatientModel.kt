package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class PatientModel (
    @SerializedName("Id")
    val id: Int,
    @SerializedName("UsuarioId")
    val userId: Int,
    @SerializedName("Usuario")
    val user: User,
    @SerializedName("FechaNacimiento")
    val bornDate: String,
    @SerializedName("Genero")
    val gender: String,
    @SerializedName("Diagnostico")
    val diagnostic: String,
    @SerializedName("Direccion")
    val address: String,
    @SerializedName("Actico")
    val isActive: Boolean,
    @SerializedName("PsicologoAsignadoId")
    val psychologistAsignedId: Int,
    @SerializedName("PsicologoAsignado")
    val psychologist: PsychologistModel,
    @SerializedName("DiarioEmocional")
    val diary: DairyModel
)