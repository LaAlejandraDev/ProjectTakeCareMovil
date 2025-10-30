package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiUsers
import com.example.takecare.data.models.RegisterUserModel
import com.example.takecare.data.models.User
import retrofit2.Response

class UserRepository(private val apiUsers: ApiUsers) {
    suspend fun getAllUsers(): Response<List<User>> {
        return apiUsers.getAllUsers()
    }

    suspend fun getUser(id: Int): Response<User> {
        return apiUsers.getUser(id)
    }

    suspend fun addUser(user: RegisterUserModel): Response<RegisterUserModel> {
        return apiUsers.addUser(user)
    }
}