package com.example.takecare.ui.screens.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.User
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser : StateFlow<User?> = _selectedUser

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
}