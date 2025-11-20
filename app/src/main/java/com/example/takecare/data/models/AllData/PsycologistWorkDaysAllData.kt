package com.example.takecare.data.models.AllData

import com.google.gson.annotations.SerializedName

data class PsycologistWorkDaysAllData (
    @SerializedName("id")
    val id: Int,

    @SerializedName("idPsicologo")
    val idPsycologist: Int,

    @SerializedName("fecha")
    val date: String,

    @SerializedName("cupoMaximo")
    val max: Int,

    @SerializedName("citasAgendadas")
    val currentDates: Int
)