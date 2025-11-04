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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    private val _isVerifyingSession = MutableStateFlow(false)

    val isVerifyingSession: StateFlow<Boolean> = _isVerifyingSession

    private val _isTokenSaved = MutableStateFlow<Boolean>(false)
    val isTokenSaved : StateFlow<Boolean> = _isTokenSaved

    fun loginUser(email: String, password: String, context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            try {
                val response = RetrofitClient.ApiLoginUsers.loginUser(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    sessionManager.saveSession(
                        token = body.token,
                        userId = body.user.id,
                        userName = body.user.name,
                        email = body.user.email,
                    )
                    Log.i("TOKENGUARDADO", body.token)
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
            _isVerifyingSession.value = true

            val token = sessionManager.getToken().firstOrNull()
            _isTokenSaved.value = token != null

            Log.i("TokenGuardado", "Estado del token: $isTokenSaved")

            if (!token.isNullOrEmpty()) {
                Log.i("TokenLogin", "Sí hay token: $token")
                try {
                    val response = RetrofitClient.ApiLoginUsers.verifyToken(mapOf("token" to token))
                    if (response.isSuccessful) {
                        Log.i("TokenLogin", "Token válido")
                        _loginSuccess.value = true
                    } else {
                        Log.w("TokenLogin", "Token inválido, limpiando sesión")
                        sessionManager.clearSession()
                        _loginSuccess.value = false
                    }
                } catch (e: Exception) {
                    Log.e("TokenLogin", "Error verificando token: ${e.message}")
                    sessionManager.clearSession()
                    _loginSuccess.value = false
                }
            } else {
                Log.i("TokenLogin", "No hay token guardado")
                _loginSuccess.value = false
            }

            delay(1000)
            _isVerifyingSession.value = false

            Log.i("LoginState", "EL login fue: ${_loginSuccess.value}")
        }
    }
}