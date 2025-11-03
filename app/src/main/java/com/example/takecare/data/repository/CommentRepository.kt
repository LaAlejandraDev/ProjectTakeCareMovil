package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiComments
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.CreateComment
import retrofit2.Response

class CommentRepository(private val apiService: ApiComments) {
    suspend fun getPostCommets(idPost: Int): Response<List<Comment>> {
        return apiService.getPostCommets(idPost)
    }

    suspend fun addComment(comment: CreateComment): Response<Comment> {
        return apiService.addComment(comment)
    }
}