package com.example.takecare.data.client

import com.example.takecare.data.interfaces.ApiForum
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "" // TODO: Agregar la url de los end points


    /// Cliente para poder hacer peticiones en el apartado del foro
    val ApiServerForum: ApiForum by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServerForum::class.java)
    }
}