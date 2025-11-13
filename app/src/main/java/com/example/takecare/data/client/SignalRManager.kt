package com.example.takecare.data.client

import android.util.Log
import com.example.takecare.data.models.AllData.MessageAllDataModel
import com.example.takecare.data.models.Insert.MessageModelCreate
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SignalRManager {
    private const val HUB_URL = "http://192.168.0.107:5002/chatHub"
    private var hubConnection: HubConnection? = null
    private val gson = Gson()

    private val _messages = MutableSharedFlow<MessageAllDataModel>(
        replay = 1,
        extraBufferCapacity = 1
    )
    val messages = _messages.asSharedFlow()

    fun startConnection() {
        if (hubConnection == null) {
            hubConnection = HubConnectionBuilder.create(HUB_URL).build()
        }

        hubConnection?.on("ReceiveMessage", { messageJson: LinkedHashMap<*, *> ->
            try {
                val jsonStr = gson.toJson(messageJson)
                val message = gson.fromJson(jsonStr, MessageAllDataModel::class.java)

                Log.i("SIGNALR", "Mensaje recibido: ${message.senderId} -> ${message.message}")
                _messages.tryEmit(message)
            } catch (e: Exception) {
                Log.e("SIGNALR", "Error al deserializar mensaje: ${e.message}")
            }
        }, LinkedHashMap::class.java)

        hubConnection?.start()
            ?.doOnComplete { Log.i("SIGNALR", "Conectado al servidor SignalR") }
            ?.doOnError { e -> Log.e("SIGNALR", "Error al conectar: ${e.message}") }
            ?.subscribe()
    }

    fun sendMessage(message: MessageModelCreate) {
        try {
            hubConnection?.send("SendMessage", message)
            Log.i("SIGNALR", "Mensaje enviado por SignalR: $message")
        } catch (e: Exception) {
            Log.e("SIGNALR", "Error al enviar mensaje: ${e.message}")
        }
    }

    fun stopConnection() {
        hubConnection?.stop()
        hubConnection = null
    }
}