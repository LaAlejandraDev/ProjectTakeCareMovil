package com.example.takecare.ui.screens.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.LoginRequest
import com.example.takecare.data.models.UserSession
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.launch

class LoginViewModel(
) : ViewModel() {
    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    fun loginUser(email: String, password: String, context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            try {
                val response = RetrofitClient.ApiLoginUsers.loginUser(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {

                    sessionManager.saveSession(
                        token = response.body()?.token ?: "",
                        userId = response.body()?.user?.id ?: -1,
                        userName = response.body()?.user?.name ?: "",
                        email = response.body()?.user?.email ?: ""
                    )

                    _loginSuccess.value = true
                } else {
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                _loginSuccess.value = false
            }
        }
    }

    fun verifUserLogged(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)

            sessionManager.getToken().collect { token ->
                if (!token.isNullOrEmpty()) {
                    try {
                        val response = RetrofitClient.ApiLoginUsers.verifyToken(mapOf("token" to token))
                        Log.i("TOKEN", "TOKEN: ${sessionManager.getToken()}")
                        _loginSuccess.value = response.isSuccessful
                    } catch (e: Exception) {
                        _loginSuccess.value = false
                    }
                } else {
                    _loginSuccess.value = false
                }
            }
        }
    }

}

