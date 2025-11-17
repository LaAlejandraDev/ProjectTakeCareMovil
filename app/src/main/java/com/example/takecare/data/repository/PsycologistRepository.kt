package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiPsycologist
import com.example.takecare.data.models.AllData.PsycologistAllData
import retrofit2.Response

class PsycologistRepository(private val apiPsycologist: ApiPsycologist) {
    suspend fun getAllPsycologist(): Response<List<PsycologistAllData>> {
        return apiPsycologist.getAllPsycologist()
    }
}