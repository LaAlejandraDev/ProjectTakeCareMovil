package com.example.takecare.data.interfaces

import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.data.models.PatientModel
import com.example.takecare.data.models.RegisterUserModel
import com.example.takecare.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiUsers {
    @GET("Usuarios")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("Usuarios/{id}")
    suspend fun getUser(@Path("id") id: Int): Response<User>

    @POST("Pacientes")
    suspend fun addUser(@Body user: PatientModel): Response<PatientModel>

    @GET("Pacientes/usuario/{id}")
    suspend fun getPatient(@Path("id") id: Int): Response<PatientModel>
    @PUT("Pacientes/{id}")
    suspend fun updatePatient(@Path("id") id: Int, @Body patientModel: PatientModel): Response<PatientModel>

    @GET("Citas/paciente/{idPatient}")
    suspend fun getPatientDates(@Path("idPatient") id: Int): Response<List<DateModelCreate>>
}