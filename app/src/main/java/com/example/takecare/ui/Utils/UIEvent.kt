package com.example.takecare.ui.Utils

sealed class UIEvent {
    data class Showsnackbar(val message: String): UIEvent()
}