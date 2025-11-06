package com.example.takecare.data.models
import com.google.gson.annotations.SerializedName

data class PsychologistModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("idUsuario")
    val idUsuario: Int,

    @SerializedName("cedulaProfesional")
    val cedulaProfesional: String,

    @SerializedName("especialidad")
    val especialidad: String,

    @SerializedName("descripcion")
    val descripcion: String,

    @SerializedName("experienciaAnios")
    val experienciaAnios: Int,

    @SerializedName("universidadEgreso")
    val universidadEgreso: String,

    @SerializedName("direccionConsultorio")
    val direccionConsultorio: String,

    @SerializedName("calificacionPromedio")
    val calificacionPromedio: Double,

    @SerializedName("totalResenas")
    val totalResenas: Int,

    @SerializedName("usuario")
    val usuario: User?
)