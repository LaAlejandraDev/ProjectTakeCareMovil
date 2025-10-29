package com.example.takecare.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.RegisterUserModel
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    fun createNewUser(user: RegisterUserModel) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerUsers.addUser(user)
                if (response.isSuccessful) {
                    Log.i("USERPOST", "Se agrego el usuario " + response.message())
                } else {
                    Log.e("USERPOST", "Error al agregar al usuario " + response.message())
                }
            } catch (e: Exception) {
                Log.e("USERPOST", "Error de servidor USER: " + e.message)
            }
        }
    }
}
