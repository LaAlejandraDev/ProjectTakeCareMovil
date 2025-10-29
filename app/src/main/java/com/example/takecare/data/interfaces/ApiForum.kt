package com.example.takecare.data.interfaces

import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiForum {
    @GET("getAllPost")
    suspend fun getAllPost(): Response<List<Post>>

    @GET("getPost/{id}")
    suspend fun getAPost(@Path("id") id: Int): Response<Post>

    @POST("addPost")
    suspend fun addPost(@Body post: Post): Response<Boolean>

    @POST("likePost/{id}")
    suspend fun likePost(@Path("id") id: Int): Response<Boolean>

    @POST("responsePost/{id}")
    suspend fun responseAPost(@Path("id") id: Int, @Body comment: Comment): Response<Boolean>
}