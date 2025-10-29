package com.example.takecare.ui.screens.login

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.LoginRequest
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.ApiLoginUsers.loginUser(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    _loginSuccess.value = true
                } else {
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _loginSuccess.value = false
            }
        }
    }
}

