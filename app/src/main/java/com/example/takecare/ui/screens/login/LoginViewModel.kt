package com.example.takecare.ui.screens.login

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
    private val sessionManager: SessionManager
) : ViewModel() {
    private val _loginSuccess = mutableStateOf(false)
    val loginSuccess: State<Boolean> = _loginSuccess

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiLoginUsers.loginUser(LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val session = UserSession(
                        token = response.body()?.token ?: "",
                        userId = response.body()?.user?.id ?: -1,
                        userName = response.body()?.user?.name ?: "",
                        email = response.body()?.user?.email ?: "",
                        role = response.body()?.user?.role ?: 0
                    )

                    sessionManager.saveSession(session)

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

class LoginViewModelFactory(
    private val sessionManager: SessionManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(sessionManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

