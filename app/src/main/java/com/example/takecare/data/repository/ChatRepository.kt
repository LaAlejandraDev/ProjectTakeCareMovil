package com.example.takecare.data.repository

import ChatAllDataModel
import com.example.takecare.data.interfaces.ApiChat
import com.example.takecare.data.models.AllData.MessageAllDataModel
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.MessageModelCreate
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

    suspend fun getAllUserChats(id: Int): Response<List<ChatAllDataModel>> {
        return apiService.getUserChats(id)
    }

    suspend fun createNewMessage(message: MessageModelCreate): Response<MessageModelCreate> {
        return apiService.postNewMessage(message)
    }

    suspend fun getChat(chatId: Int): Response<ChatModel> {
        return apiService.getChat(chatId)
    }

    suspend fun getChatMessages(chatId: Int): Response<List<MessageAllDataModel>> {
        return apiService.getChatMessages(chatId)
    }

    suspend fun getChatInfo(chatId: Int): Response<ChatAllDataModel> {
        return apiService.getChatInfo(chatId)
    }
}