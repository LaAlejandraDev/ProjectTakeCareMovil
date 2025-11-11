package com.example.takecare.ui.screens.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.PatientModel
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    fun createNewUser(user: PatientModel, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("USERPOST", "Iniciando creación de usuario...")
                Log.d("USERPOST", "Datos enviados: ${user}")

                val response = RetrofitClient.ApiServerUsers.addUser(user)

                Log.d("USERPOST", "Código de respuesta: ${response.code()}")
                Log.d("USERPOST", "Mensaje del servidor: ${response.message()}")

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.i("USERPOST", "✅ Usuario creado correctamente.")
                    Log.i("USERPOST", "Respuesta del servidor: ${responseBody}")
                    onResult(true)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("USERPOST", "❌ Error al crear usuario.")
                    Log.e("USERPOST", "Código HTTP: ${response.code()}")
                    Log.e("USERPOST", "Mensaje: ${response.message()}")
                    Log.e("USERPOST", "ErrorBody: ${errorBody ?: "Sin cuerpo de error"}")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("USERPOST", "💥 Excepción al crear usuario: ${e.localizedMessage}")
                e.printStackTrace()
                onResult(false)
            }
        }
    }
}
