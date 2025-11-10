package com.example.takecare.ui.screens.messages

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.MessageModelCreate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    private val _chatData = MutableStateFlow<ChatModel?>(null)
    val chatData : StateFlow<ChatModel?> = _chatData

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

    fun createNewMessage(newMessage: MessageModelCreate) {
        viewModelScope.launch {
            Log.i("MESSAGE_SEND","MENSAJE: ${newMessage.toString()}")
            try {
                val response = RetrofitClient.ApiServerChats.postNewMessage(newMessage)
                if (response.isSuccessful) {
                    Log.i("MESSAGE_SEND","El mensaje se envio de manera correcta")
                } else {
                    Log.e("MESSAGE_ERROR","Error al enviar el mensaje")
                    Log.e("MESSAGE_ERROR","ERROR: ${response.body()} ${response.errorBody()} ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("MESSAGE_ERROR","Error de servidor")
            }
        }
    }
}