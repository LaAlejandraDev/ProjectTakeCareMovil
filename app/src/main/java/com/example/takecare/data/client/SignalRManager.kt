package com.example.takecare.data.client

import android.util.Log
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object SignalRManager {
    private const val HUB_URL = "http://192.168.0.107:5002/chathub"
    private var hubConnection: HubConnection? = null

    private val _messages = MutableSharedFlow<Pair<String, String>>(replay = 0)
    val messages = _messages.asSharedFlow()

    fun startConnection() {
        if (hubConnection == null) {
            hubConnection = HubConnectionBuilder.create(HUB_URL).build()
        }

        hubConnection?.on("ReceiveMessage", { user: String, message: String ->
            Log.i("SIGNALR", "Mensaje recibido: $user -> $message")
            _messages.tryEmit(user to message)
        }, String::class.java, String::class.java)

        try {
            hubConnection?.start()?.blockingAwait()
            Log.i("SIGNALR", "Conectado al servidor SignalR")
        } catch (e: Exception) {
            Log.e("SIGNALR", "Error al conectar: ${e.message}")
        }
    }

    fun sendMessage(senderId: Int, message: String) {
        try {
            hubConnection?.send("SendMessage", senderId.toString(), message)
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