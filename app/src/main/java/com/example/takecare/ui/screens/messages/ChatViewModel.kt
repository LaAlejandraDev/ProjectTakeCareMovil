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
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatData = MutableStateFlow<ChatModel?>(null)
    val chatData : StateFlow<ChatModel?> = _chatData

    private val _chatMessagesData = MutableStateFlow<List<MessageAllDataModel>>(emptyList())
    val chatMessagesData : StateFlow<List<MessageAllDataModel>> = _chatMessagesData

    private val _chatInfo = MutableStateFlow<ChatAllDataModel?>(null)
    val chatInfo : StateFlow<ChatAllDataModel?> = _chatInfo


    init {
        viewModelScope.launch {
            SignalRManager.messages.collect { (user, message) ->
                val newMsg = MessageAllDataModel(
                    id = -1,
                    chatId = 0,
                    senderId = user.toIntOrNull() ?: 0,
                    message = message,
                    readed = false,
                    date = ""
                )
                _chatMessagesData.value += newMsg
            }
        }
    }

    fun connectSignalR() {
        SignalRManager.startConnection()
    }

    fun sendSignalRMessage(senderId: Int, message: String) {
        SignalRManager.sendMessage(senderId, message)
    }

    fun setChatMessages(newMessages: List<MessageAllDataModel>) {
        _chatMessagesData.value = newMessages
    }

    fun getChatInfo(chatId: Int) {
        Log.i("CHAT_GET","ID recolectado: $chatId")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChatInfo(chatId)
                if (response.isSuccessful) {
                    val chatData = response.body()
                    Log.i("CHAT_INFO","El chat se cargo de manera correcta")
                    _chatInfo.value = chatData
                } else {
                    Log.e("CHAT_INFO_ERROR","Error al cargar el chat")
                }
            } catch (e: Exception) {
                Log.e("CHAT_INFO_SERVER_ERROR","Error de servidor")
            }
        }
    }

    fun getChatData(chatId: Int) {
        Log.i("CHAT_GET","ID recolectado: $chatId")
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChat(chatId)
                if (response.isSuccessful) {
                    val chatData = response.body()
                    Log.i("CHAT_GET","El chat se cargo de manera correcta")
                    _chatData.value = chatData
                } else {
                    Log.e("CHAT_ERROR","Error al cargar el chat")
                }
            } catch (e: Exception) {
                Log.e("CHAT_SERVER_ERROR","Error de servidor")
            }
        }
    }

    fun createNewMessage(
        newMessage: MessageModelCreate,
        onResult: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            Log.i("MESSAGE_SEND", "MENSAJE: ${newMessage}")
            try {
                val response = RetrofitClient.ApiServerChats.postNewMessage(newMessage)
                if (response.isSuccessful) {
                    Log.i("MESSAGE_SEND", "El mensaje se envió correctamente")
                    onResult(true)
                } else {
                    Log.e("MESSAGE_ERROR", "Error al enviar el mensaje: ${response.code()}")
                    Log.e("MESSAGE_ERROR_BODY", "BODY: ${response.errorBody()?.string()}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("MESSAGE_ERROR", "Error de servidor: ${e.message}")
                onResult(false)
            }
        }
    }

    fun getChatMessages(chatId: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getChatMessages(chatId)
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    Log.i("MESSAGE_LIST","Los mensajes se obtuvieron de manera correcta")
                    _chatMessagesData.value = list
                } else {
                    Log.e("MESSAGE_LIST_ERROR","Error al obtener los mensajes del chat")
                }
            } catch (e: Exception) {
                Log.e("MESSAGE_LIST_SERVER_ERROR","Error de servidor")
            }
        }
    }
}