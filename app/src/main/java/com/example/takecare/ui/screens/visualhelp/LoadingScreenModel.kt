package com.example.takecare.ui.screens.visualhelp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoadingScreenModel : ViewModel() {
    private val _message = MutableStateFlow<String>("")
    val message : StateFlow<String> = _message


}