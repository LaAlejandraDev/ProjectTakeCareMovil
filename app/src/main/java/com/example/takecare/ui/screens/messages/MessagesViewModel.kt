package com.example.takecare.ui.screens.messages

import ChatAllDataModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.PsychologistModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MessagesViewModel : ViewModel() {
    private val _chatList = MutableStateFlow<List<ChatAllDataModel>>(emptyList())
    val chatList: StateFlow<List<ChatAllDataModel>> = _chatList

    private val _psycoList = MutableStateFlow<List<PsychologistModel>>(emptyList())
    val psycoList: StateFlow<List<PsychologistModel>> = _psycoList

    private fun getAllPsycologist() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerChats.getAllPsycologist()
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    _psycoList.value = data
                } else {
                    Log.e("CHAT", "Error al obtener la lista de psicologos")
                }
            } catch (e: kotlin.Exception) {
                Log.e("CHAT", "Error al obtener la lista de psicologos")
            }
        }
    }

        fun getAllChats(patientId: Int) {
            Log.i("CHATID", "ID DEL PACIENTE: ${patientId}")
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.ApiServerChats.getUserChats(patientId)
                    if (response.isSuccessful) {
                        val data = response.body() ?: emptyList()
                        _chatList.value = data
                    } else {
                        Log.e("CHAT", "Error al obtener la lista de chats")
                        Log.e("CHAT", "ERROR: ${response.message()}; MAS ERROR: ${response.body()}")
                    }
                } catch (e: Exception) {
                    Log.e("CHAT", "Error al obtener la lista de chats")
                }
            }
    }
}