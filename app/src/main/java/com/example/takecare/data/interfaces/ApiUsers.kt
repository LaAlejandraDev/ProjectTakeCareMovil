package com.example.takecare.data.interfaces

import com.example.takecare.data.models.RegisterUserModel
import com.example.takecare.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiUsers {
    @GET("Usuarios")
    suspend fun getAllUsers(): Response<List<User>>

    @GET("Usuarios")
    suspend fun getUser(@Query("Id") id: Int): Response<List<User>>

    @POST("Usuarios")
    suspend fun addUser(@Body user: RegisterUserModel): Response<RegisterUserModel>
}