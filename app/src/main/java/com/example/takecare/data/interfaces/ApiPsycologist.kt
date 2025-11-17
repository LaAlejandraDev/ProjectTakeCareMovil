package com.example.takecare.data.interfaces

import com.example.takecare.data.models.AllData.PsycologistAllData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiPsycologist {
    @GET("Psicologos")
    suspend fun getAllPsycologist(): Response<List<PsycologistAllData>>
}