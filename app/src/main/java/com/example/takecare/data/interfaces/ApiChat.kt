package com.example.takecare.data.interfaces

import ChatAllDataModel
import com.example.takecare.data.models.AllData.MessageAllDataModel
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.MessageModelCreate
import com.example.takecare.data.models.PsychologistModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiChat {
    @GET("Chats")
    suspend fun getAllUserChats(): Response<List<ChatModel>>

    @GET("Psicologos")
    suspend fun getAllPsycologist(): Response<List<PsychologistModel>>

    @POST("Chats")
    suspend fun createNewChat(@Body newChat: ChatModel): Response<ChatModel>

    @GET("Chats/lista")
    suspend fun getUserChats(
        @Query("idPaciente") userId: Int
    ): Response<List<ChatAllDataModel>>

    @POST("ChatMensajes")
    suspend fun postNewMessage(@Body newMessage: MessageModelCreate): Response<MessageModelCreate>
    @GET("Chats/{id}")
    suspend fun getChat(@Path ("id") chatId: Int) : Response<ChatModel>

    @GET("ChatMensajes/chat/{chatId}")
    suspend fun getChatMessages(@Path ("chatId") chatId: Int): Response<List<MessageAllDataModel>>

    @GET("Chats/chatinfo/{chatId}")
    suspend fun getChatInfo(@Path ("chatId") chatId: Int): Response<ChatAllDataModel>
}