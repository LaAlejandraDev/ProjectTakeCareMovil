package com.example.takecare.ui.screens.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.User
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {
    private val _selectedUser = MutableStateFlow<User?>(null)
    val selectedUser : StateFlow<User?> = _selectedUser

    fun selectUser(context: Context) {
        val sessionManager = SessionManager(context)
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerUsers.getUser(0)
            } catch (e: Exception) {
                null
            }
        }
    }
}