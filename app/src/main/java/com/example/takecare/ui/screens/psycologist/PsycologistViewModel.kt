package com.example.takecare.ui.screens.psycologist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.AllData.PsycologistAllData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PsycologistViewModel : ViewModel() {
    private val _psycologistList = MutableStateFlow<List<PsycologistAllData>>(emptyList())
    val psycologistList : StateFlow<List<PsycologistAllData>> = _psycologistList

    fun getAllPsycologist(onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerPsycologist.getAllPsycologist()
                if (response.isSuccessful) {
                    onResponse(true)
                    val data = response.body() ?: emptyList()
                    _psycologistList.value = data
                } else {
                    onResponse(false)
                }
            } catch (e: Exception) {
                onResponse(false)
            }
        }
    }
}