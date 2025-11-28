package com.example.takecare.data.models.Insert

import com.google.gson.annotations.SerializedName

data class RatingModelCreate (
    @SerializedName("calificacion")
    val cal: Int,

    @SerializedName("idPsicologo")
    val psychologistId: Int,

    @SerializedName("idCita")
    val dateId: Int
)

data class RatingModelInfo (
    @SerializedName("id")
    val id: Int,

    @SerializedName("calificacion")
    val cal: Int,

    @SerializedName("idPsicologo")
    val psychologistId: Int,

    @SerializedName("idCita")
    val dateId: Int
)