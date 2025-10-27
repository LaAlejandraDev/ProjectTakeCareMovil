package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("Id")
    val id: Int,
    @SerializedName("Nombre")
    val name: String,
    @SerializedName("ApellidoPaterno")
    val firstLastName: String,
    @SerializedName("ApellidoMaterno")
    val secondLastName: String,
    @SerializedName("Correo")
    val email: String,
    @SerializedName("Telefono")
    val phone: String,
    @SerializedName("Country")
    val country: String,
    @SerializedName("Contrasena")
    val password: String,
    @SerializedName("imagen")
    val imageUrl: String? = null,
    @SerializedName("Rol")
    val type: UserType,
    @SerializedName("Activo")
    val isActive: Boolean,
    @SerializedName("FechaRegistro")
    val createdAt: String,
    @SerializedName("UltimoAcceso")
    val lastAccess: String,
) {
    fun getInitials(): String {
        return name.take(1).uppercase();
    }
}

enum class UserType(val displayName: String) {
    PATIENT("Paciente"),
    PSICOLOGIST("Psicologo"),
    ADMIN("Administrador")
}

