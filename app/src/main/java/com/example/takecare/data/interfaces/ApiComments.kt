package com.example.takecare.data.interfaces

import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.CreateComment
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiComments {
    @GET("comentarios/Post/{idPost}")
    suspend fun getPostCommets(@Path("idPost")idPost: Int): Response<List<Comment>>

    @POST("comentarios")
    suspend fun addComment(@Body comment: CreateComment): Response<Comment>
}