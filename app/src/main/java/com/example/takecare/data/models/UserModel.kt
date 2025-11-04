package com.example.takecare.data.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("nombre")
    val name: String? = null,

    @SerializedName("apellidoPaterno")
    val firstLastName: String? = null,

    @SerializedName("apellidoMaterno")
    val secondLastName: String? = null,

    @SerializedName("correo")
    val email: String? = null,

    @SerializedName("telefono")
    val phone: String? = null,

    @SerializedName("contrasena")
    val password: String? = null,

    @SerializedName("genero")
    val gender: String?,

    @SerializedName("rol")
    val type: Int? = null,

    @SerializedName("activo")
    val isActive: Boolean? = null,

    @SerializedName("fechaRegistro")
    val createdAt: String? = null,

    @SerializedName("ultimoAcceso")
    val lastAccess: String? = null
) {
    fun getInitials(): String {
        return name!!.take(1).uppercase();
    }

    fun getUserType(): String {
        val index = type

        return when (index) {
            0 -> UserType.PATIENT.displayName
            1 -> UserType.PSICOLOGIST.displayName
            2 -> UserType.ADMIN.displayName
            else -> "Desconocido"
        }
    }

}

enum class UserType(val displayName: String) {
    PATIENT("Paciente"),
    PSICOLOGIST("Psicologo"),
    ADMIN("Administrador")
}

