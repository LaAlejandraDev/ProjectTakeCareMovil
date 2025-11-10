package com.example.takecare.data.models.AllData

import com.google.gson.annotations.SerializedName

data class MessageAllDataModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("idChat")
    val chatId: Int,
    @SerializedName("idRemitenteUsuario")
    val senderId: Int,
    @SerializedName("mensaje")
    val message: String,
    @SerializedName("leido")
    val readed: Boolean,
    @SerializedName("fecha")
    val date: String
)

//public int Id { get; set; }
//public int IdChat { get; set; }
//public int IdRemitenteUsuario { get; set; }
//public string Mensaje { get; set; } = null!;
//public bool Leido { get; set; } = false;
//public DateTime Fecha { get; set; } = DateTime.UtcNow;