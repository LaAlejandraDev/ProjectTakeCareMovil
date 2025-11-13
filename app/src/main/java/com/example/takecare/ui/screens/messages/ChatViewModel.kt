package com.example.takecare.ui.screens.messages

import ChatAllDataModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.client.SignalRManager
import com.example.takecare.data.models.AllData.MessageAllDataModel
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.MessageModelCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _chatData = MutableStateFlow<ChatModel?>(null)
    val chatData: StateFlow<ChatModel?> = _chatData

    private val _chatMessagesData = MutableStateFlow<List<MessageAllDataModel>>(emptyList())
    val chatMessagesData: StateFlow<List<MessageAllDataModel>> = _chatMessagesData

    private val _chatInfo = MutableStateFlow<ChatAllDataModel?>(null)
    val chatInfo: StateFlow<ChatAllDataModel?> = _chatInfo


    init {
        viewModelScope.launch {
            SignalRManager.messages.collect { newMsg ->
                Log.i("SIGNALR_RECEIVED", "Nuevo mensaje recibido: ${newMsg.message}")
                _chatMessagesData.update { oldList -> oldList + newMsg }
            }
        }
    }

    fun connectSignalR() {
        Log.i("SIGNALR", "Intentando conectar a SignalR...")
        SignalRManager.startConnection()
    }

    fun sendSignalRMessage(message: MessageModelCreate) {
        Log.i("SIGNALR_SEND", "Enviando mensaje por SignalR: $message")
        SignalRManager.sendMessage(message)
    }

    fun getChatInfo(chatId: Int) {
        Log.i("CHAT_INFO", "Obteniendo info del chat ID: $chatId")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChatInfo(chatId)
                if (response.isSuccessful) {
                    _chatInfo.value = response.body()
                    Log.i("CHAT_INFO_SUCCESS", "Chat info cargada correctamente")
                } else {
                    Log.e("CHAT_INFO_ERROR", "Error al cargar info del chat: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CHAT_INFO_EXCEPTION", "Error de servidor: ${e.message}")
            }
        }
    }

    fun getChatData(chatId: Int) {
        Log.i("CHAT_DATA", "Obteniendo datos del chat ID: $chatId")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChat(chatId)
                if (response.isSuccessful) {
                    _chatData.value = response.body()
                    Log.i("CHAT_DATA_SUCCESS", "Chat cargado correctamente")
                } else {
                    Log.e("CHAT_DATA_ERROR", "Error al cargar chat: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CHAT_DATA_EXCEPTION", "Error de servidor: ${e.message}")
            }
        }
    }

    fun getChatMessages(chatId: Int) {
        Log.i("CHAT_MESSAGES", "Obteniendo mensajes del chat ID: $chatId")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChatMessages(chatId)
                if (response.isSuccessful) {
                    val list = response.body().orEmpty()
                    _chatMessagesData.value = list
                    Log.i("CHAT_MESSAGES_SUCCESS", "Mensajes obtenidos correctamente")
                } else {
                    Log.e("CHAT_MESSAGES_ERROR", "Error al obtener mensajes: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("CHAT_MESSAGES_EXCEPTION", "Error de servidor: ${e.message}")
            }
        }
    }

    fun createNewMessage(newMessage: MessageModelCreate, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            Log.i("MESSAGE_CREATE", "Creando nuevo mensaje: $newMessage")
            try {
                val response = RetrofitClient.ApiServerChats.postNewMessage(newMessage)
                if (response.isSuccessful) {
                    Log.i("MESSAGE_CREATE_SUCCESS", "Mensaje creado en la base de datos")
                    onResult(true)
                } else {
                    Log.e("MESSAGE_CREATE_ERROR", "Error al crear mensaje: ${response.code()}")
                    Log.e("MESSAGE_CREATE_BODY", "BODY: ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("MESSAGE_CREATE_EXCEPTION", "Error de servidor: ${e.message}")
                onResult(false)
            }
        }
    }

    fun setChatMessages(newMessages: List<MessageAllDataModel>) {
        _chatMessagesData.value = newMessages
    }
}