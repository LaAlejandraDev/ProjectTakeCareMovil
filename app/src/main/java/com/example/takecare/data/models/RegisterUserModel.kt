package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class RegisterUserModel (
    @SerializedName("id") val id: Int = 0,
    @SerializedName("nombre") val name: String,
    @SerializedName("apellidoPaterno") val firstLastName: String,
    @SerializedName("apellidoMaterno") val secondLastName: String,
    @SerializedName("correo") val email: String,
    @SerializedName("telefono") val phone: String,
    @SerializedName("contrasena") val password: String,
    @SerializedName("rol") val role: Int = 1,
    @SerializedName("activo") val isActive: Boolean = true,
    @SerializedName("fechaRegistro") val createdAt: String = "2025-10-29T10:06:58.672Z",
    @SerializedName("ultimoAcceso") val lastAccess: String = "2025-10-29T10:06:58.672Z"
)