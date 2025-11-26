package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiPsycologist
import com.example.takecare.data.models.AllData.PsycologistAllData
import com.example.takecare.data.models.AllData.PsycologistWorkDaysAllData
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.CreateChat
import com.example.takecare.data.models.Insert.DateModelCreate
import retrofit2.Response

class PsycologistRepository(private val apiPsycologist: ApiPsycologist) {
    suspend fun getAllPsycologist(): Response<List<PsycologistAllData>> {
        return apiPsycologist.getAllPsycologist()
    }

    suspend fun getPsycologistWorkDays(id: Int): Response<List<PsycologistWorkDaysAllData>> {
        return apiPsycologist.getWorkDays(id)
    }

    suspend fun createNewDate(date: DateModelCreate): Response<DateModelCreate> {
        return apiPsycologist.createNewDate(date)
    }

    suspend fun createNewChat(chat: CreateChat): Response<ChatModel> {
        return apiPsycologist.createNewChat(chat)
    }
}