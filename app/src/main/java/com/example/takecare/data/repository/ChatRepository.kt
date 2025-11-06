package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiChat
import com.example.takecare.data.models.ChatModel
import com.example.takecare.data.models.PsychologistModel
import retrofit2.Response

class ChatRepository(private val apiService: ApiChat) {
    suspend fun getAllUserChats(): Response<List<ChatModel>> {
        return apiService.getAllUserChats()
    }

    suspend fun getAllPsycologist(): Response<List<PsychologistModel>> {
        return apiService.getAllPsycologist()
    }

    suspend fun createNewChat(newChat: ChatModel): Response<ChatModel> {
        return apiService.createNewChat(newChat)
    }
}