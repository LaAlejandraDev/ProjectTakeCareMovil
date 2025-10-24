package com.example.takecare.data.models

data class User (
    val id: Int,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val userName: String,
    val password: String,
    val imageUrl: String? = null,
    val type: UserType,
    val isActive: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val deletedAt: String
) {
    fun getInitials(): String {
        return userName.take(1).uppercase();
    }
}

enum class UserType(val displayName: String) {
    PATIENT("Paciente"),
    PSICOLOGIST("Psicologo"),
    ADMIN("Administrador")
}

