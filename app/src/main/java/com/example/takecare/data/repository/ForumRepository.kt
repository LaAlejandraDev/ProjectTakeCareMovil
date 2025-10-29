package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiForum
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post
import retrofit2.Response

class ForumRepository(private val apiService: ApiForum) {
    suspend fun getAllPost(): Response<List<Post>> {
        return apiService.getAllPost()
    }

    suspend fun getAPost(id: Int): Response<List<Post>> {
        return apiService.getAPost(id)
    }

    suspend fun addPost(post: Post): Boolean {
        val response = apiService.addPost(post)
        return response.isSuccessful
    }

    suspend fun likeAPost(id: Int): Boolean {
        val response = apiService.likePost(id)
        return response.isSuccessful
    }

    suspend fun responseAPost(id: Int, comment: Comment): Boolean {
        val response = apiService.responseAPost(id, comment)
        return response.isSuccessful
    }
}