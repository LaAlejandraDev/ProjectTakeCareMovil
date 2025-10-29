package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("usuario") val user: LoggedUser
)

data class LoggedUser(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("correo") val email: String,
    @SerializedName("rol") val role: Int,
    @SerializedName("ultimoAcceso") val lastAccess: String
)

data class LoginRequest(
    @SerializedName("correo") val email: String,
    @SerializedName("contrasena") val password: String
)