package com.example.takecare.data.interfaces

import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Insert.PostModelCreate
import com.example.takecare.data.models.Post
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiForum {
    @GET("Posts")
    suspend fun getAllPost(): Response<List<Post>>

    @GET("Posts/{id}")
    suspend fun getAPost(@Path("id") id: Int): Response<Post>

    @POST("Posts")
    suspend fun addPost(@Body post: PostModelCreate): Response<Post>

    @POST("likePost/{id}")
    suspend fun likePost(@Path("id") id: Int): Response<Boolean>

    @POST("responsePost/{id}")
    suspend fun responseAPost(@Path("id") id: Int, @Body comment: Comment): Response<Boolean>
}