package com.example.takecare.data.interfaces

import com.example.takecare.data.models.LoginRequest
import com.example.takecare.data.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiLogin   {
    @POST("LoginSesion")
    suspend fun loginUser(@Body credentials: LoginRequest): Response<LoginResponse>

    @POST("LoginSesion/verify-token")
    suspend fun verifyToken(@Body token: Map<String, String>): Response<Unit>
}