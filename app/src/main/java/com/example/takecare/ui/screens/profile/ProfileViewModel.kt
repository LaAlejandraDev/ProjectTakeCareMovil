package com.example.takecare.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.compose.ui.res.integerResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.data.models.Insert.RatingModelCreate
import com.example.takecare.data.models.PatientModel
import com.example.takecare.data.models.User
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser : StateFlow<User?> = _selectedUser
    private val _selectedPatient = MutableStateFlow<PatientModel?>(null)
    val selectedPatient : StateFlow<PatientModel?> = _selectedPatient

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _isUpdating = MutableStateFlow(false)
    val isUpdating: StateFlow<Boolean> = _isUpdating

    private val _patientDateList = MutableStateFlow<List<DateModelCreate>>(emptyList())
    val patientDateList : StateFlow<List<DateModelCreate>> = _patientDateList
    fun selectUser(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            val idUser = sessionManager.getUserId().firstOrNull()
            try {
                if (idUser != null) {
                    val response = RetrofitClient.ApiServerUsers.getUser(idUser)
                    if (response.isSuccessful) {
                        _selectedUser.value = response.body()
                        Log.i("GETUSER", "Valores obtenidos " + response.body())
                        Log.i("GETUSER", "Se obtuvo el usuario con exito " + selectedUser.value?.name)
                    } else {
                        Log.e("GETUSER", "Error al obtener el usuario " + response.message())
                    }
                } else {
                    Log.e("GETUSER", "El usuario cargado es nulo")
                }
            } catch (e: Exception) {
                Log.e("GETUSER", "Error de excepcion " + e.message)
            }
        }
    }

    fun getPatient(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            val idUser = sessionManager.getUserId().firstOrNull()
            try {
                if (idUser != null) {
                    _isLoading.value = true
                    val response = RetrofitClient.ApiServerUsers.getPatient(idUser)
                    if (response.isSuccessful) {
                        _selectedPatient.value = response.body()
                    } else {
                        Log.e("GET_PATIENT", "Error al obtener el usuario " + response.message())
                    }
                } else {
                    Log.e("GET_PATIENT", "El usuario cargado es nulo")
                }
            } catch (e: Exception) {
                Log.e("GET_PATIENT", "Error de excepcion " + e.message)
            }  finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePatient(context: Context, updatedPatient: PatientModel) {
        viewModelScope.launch {
            _isUpdating.value = true
            try {
                val idPatient = updatedPatient.id
                val response = RetrofitClient.ApiServerUsers.updatePatient(idPatient!!, updatedPatient)
            } catch (e: Exception) {

            } finally {
                _isUpdating.value = false
            }
        }
    }

    fun closeSession(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            sessionManager.clearSession()
        }
    }

    fun getPatientDatesList(onResult: (Boolean) -> Unit) {
        var idPatient: Int = -1
        viewModelScope.launch {
            try {
                selectedPatient.value?.let { item ->
                    idPatient = item.id ?: 0
                }
                val response = RetrofitClient.ApiServerUsers.getPatientDates(idPatient)
                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    _patientDateList.value = data
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }

    fun createNewRating(rating: RatingModelCreate, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerUsers.createNewRating(rating)
                if (response.isSuccessful) {
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
}