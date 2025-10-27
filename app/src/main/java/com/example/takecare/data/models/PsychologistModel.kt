package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class PsychologistModel (
    @SerializedName("Id")
    val id: Int,
    @SerializedName("UsuarioId")
    val userId: Int,
    @SerializedName("Usuario")
    val user: User,
    @SerializedName("Especialidad")
    val specialty: String,
    @SerializedName("CedulaProfesional")
    val professionalID: String,
    @SerializedName("Matricula")
    val tuition: String,
    @SerializedName("ReferenciaProfesional")
    val professionalReference: String,
    @SerializedName("Activo")
    val isActive: Boolean,
    @SerializedName("Pacientes")
    val patientList: List<PatientModel?>
)