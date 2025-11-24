package com.example.takecare.ui.screens.dairy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DiaryViewModel : ViewModel() {
    fun createNewDiary(onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // Enviar los datos al diario
            } catch (e: Exception) {
                // Manejar errores
            }
        }
    }
}