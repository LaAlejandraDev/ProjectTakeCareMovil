package com.example.takecare.data.repository

import com.example.takecare.data.interfaces.ApiUsers
import com.example.takecare.data.models.AllData.DiaryAllDataModel
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.data.models.Insert.DiaryInsertModel
import com.example.takecare.data.models.Insert.RatingModelCreate
import com.example.takecare.data.models.Insert.RatingModelInfo
import com.example.takecare.data.models.PatientModel
import com.example.takecare.data.models.User
import retrofit2.Response

class UserRepository(private val apiUsers: ApiUsers) {
    suspend fun getAllUsers(): Response<List<User>> {
        return apiUsers.getAllUsers()
    }

    suspend fun getUser(id: Int): Response<User> {
        return apiUsers.getUser(id)
    }

    suspend fun addUser(user: PatientModel): Response<PatientModel> {
        return apiUsers.addUser(user)
    }

    suspend fun getPatient(id: Int): Response<PatientModel> {
        return apiUsers.getPatient(id)
    }

    suspend fun updatePatient(id: Int, patientModel: PatientModel): Response<PatientModel> {
        return apiUsers.updatePatient(id, patientModel)
    }

    suspend fun getPatientDates(id: Int): Response<List<DateModelCreate>> {
        return apiUsers.getPatientDates(id)
    }

    suspend fun createNewDiary(newDiary: DiaryInsertModel): Response<DiaryAllDataModel> {
        return apiUsers.createNewDiary(newDiary)
    }

    suspend fun getDiaryList(id: Int): Response<List<DiaryAllDataModel>> {
        return apiUsers.getDiaryList(id)
    }

    suspend fun createNewRating(rating: RatingModelCreate): Response<RatingModelInfo> {
        return apiUsers.createNewRating(rating)
    }

    suspend fun updateImageUrl(url: String, userId: Int): Response<String> {
        return apiUsers.updateImageUrl(userId, url)
    }
}