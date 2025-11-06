package com.example.takecare.data.interfaces

import com.example.takecare.data.models.ChatModel
import com.example.takecare.data.models.PsychologistModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiChat {
    @GET("Chats")
    suspend fun getAllUserChats(): Response<List<ChatModel>>

    @GET("Psicologos")
    suspend fun getAllPsycologist(): Response<List<PsychologistModel>>

    @POST("Chats")
    suspend fun createNewChat(@Body newChat: ChatModel): Response<ChatModel>

}