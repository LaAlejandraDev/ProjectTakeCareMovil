package com.example.takecare.ui.screens.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.LoginRequest
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private val _isVerifyingSession = MutableStateFlow(false)
    val isVerifyingSession: StateFlow<Boolean> = _isVerifyingSession

    fun loginUser(email: String, password: String, context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            try {
                val response = RetrofitClient.ApiLoginUsers.loginUser(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    sessionManager.saveSession(
                        token = body.token ?: "",
                        userId = body.user?.id ?: -1,
                        userName = body.user?.name ?: "",
                        email = body.user?.email ?: ""
                    )
                    _loginSuccess.value = true
                } else {
                    _loginSuccess.value = false
                }
            } catch (e: Exception) {
                Log.e("LOGIN_ERROR", e.message.toString())
                _loginSuccess.value = false
            }
        }
    }

    fun verifyUserLogged(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            val token = sessionManager.getToken().firstOrNull()
            Log.i("Token", token?.isEmpty().toString())
            if (!token.isNullOrEmpty()) {
                try {
                    _isVerifyingSession.value = true
                    val response = RetrofitClient.ApiLoginUsers.verifyToken(mapOf("token" to token))
                    if (response.isSuccessful) {
                        Log.e("LOGIN_TOKEN", token)
                        Log.e("SERVER_RESPONSE", response.message())
                        _loginSuccess.value = true
                    } else {
                        sessionManager.clearSession()
                        _loginSuccess.value = false
                    }
                } catch (e: Exception) {
                    Log.e("VERIFY_ERROR", e.message.toString())
                    sessionManager.clearSession()
                    _loginSuccess.value = false
                }
            } else {
                _loginSuccess.value = false
                _isVerifyingSession.value = false
            }

            _isVerifyingSession.value = false
        }
    }
}