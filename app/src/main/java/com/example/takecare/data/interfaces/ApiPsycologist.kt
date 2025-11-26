package com.example.takecare.data.interfaces

import com.example.takecare.data.models.AllData.PsycologistAllData
import com.example.takecare.data.models.AllData.PsycologistWorkDaysAllData
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.CreateChat
import com.example.takecare.data.models.Insert.DateModelCreate
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiPsycologist {
    @GET("Psicologos")
    suspend fun getAllPsycologist(): Response<List<PsycologistAllData>>

    @GET("PsicologoDisponibilidades/psicologo/{idPsicologo}")
    suspend fun getWorkDays(@Path("idPsicologo")id: Int): Response<List<PsycologistWorkDaysAllData>>

    @POST("Citas")
    suspend fun createNewDate(@Body date: DateModelCreate): Response<DateModelCreate>

    @POST("Chats")
    suspend fun createNewChat(@Body chat: CreateChat): Response<ChatModel>
}