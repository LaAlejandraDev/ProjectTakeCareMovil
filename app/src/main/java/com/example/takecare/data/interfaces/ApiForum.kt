package com.example.takecare.data.interfaces

import com.example.takecare.data.models.Post
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiForum {
    @GET("post")
    suspend fun getAllPost(): List<Post>

    @GET("getPost/{id}")
    suspend fun getPost(@Path("id") id: Int): Post

    @POST("likePost/{id}")
    suspend fun likePost(@Path("id") id: Int): Post
}