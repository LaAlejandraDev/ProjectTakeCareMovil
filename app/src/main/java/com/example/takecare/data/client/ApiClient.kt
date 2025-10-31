package com.example.takecare.data.client

import com.example.takecare.data.interfaces.ApiForum
import com.example.takecare.data.interfaces.ApiLogin
import com.example.takecare.data.interfaces.ApiUsers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.0.107:5002/api/"

    val ApiServerForum: ApiForum by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiForum::class.java)
    }

    val ApiServerUsers: ApiUsers by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiUsers::class.java)
    }

    val ApiLoginUsers: ApiLogin by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiLogin::class.java)
    }
}