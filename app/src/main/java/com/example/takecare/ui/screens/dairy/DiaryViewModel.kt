package com.example.takecare.ui.screens.dairy

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.AllData.DiaryAllDataModel
import com.example.takecare.data.models.Insert.DiaryInsertModel
import com.example.takecare.data.models.PatientModel
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

sealed class DiaryState {
    object Idle : DiaryState()
    object Loading : DiaryState()
    data class Success(val message: String) : DiaryState()
    data class Error(val error: String) : DiaryState()
}

class DiaryViewModel : ViewModel() {
    private val _selectedPatient = MutableStateFlow<PatientModel?>(null)
    val selectedPatient : StateFlow<PatientModel?> = _selectedPatient

    private val _diaryResponse = MutableStateFlow<DiaryAllDataModel?>(null)
    val diaryResponse : StateFlow<DiaryAllDataModel?> = _diaryResponse

    private val _diaryState = MutableStateFlow<DiaryState>(DiaryState.Loading)
    val diaryState : StateFlow<DiaryState> = _diaryState

    private val _diaryList = MutableStateFlow<List<DiaryAllDataModel>>(emptyList())
    val diaryList : StateFlow<List<DiaryAllDataModel>> = _diaryList

    fun getPatient(context: Context) {
        viewModelScope.launch {
            val sessionManager = SessionManager(context)
            val idUser = sessionManager.getUserId().firstOrNull()
            try {
                if (idUser != null) {
                    val response = RetrofitClient.ApiServerUsers.getPatient(idUser)
                    if (response.isSuccessful) {
                        _selectedPatient.value = response.body()
                        Log.d("GET_PATIENT", "ID: ${_selectedPatient.value?.id}")
                    } else {
                        Log.e("GET_PATIENT", "Error al obtener el usuario " + response.message())
                    }
                } else {
                    Log.e("GET_PATIENT", "El usuario cargado es nulo")
                }
            } catch (e: Exception) {
                Log.e("GET_PATIENT", "Error de excepcion " + e.message)
            }
        }
    }

    fun getDiaryList(onResponse: (Boolean) -> Unit) {
        var idPatient: Int = -1

        viewModelScope.launch {
            selectedPatient.value?.let { item ->
                idPatient = item.id ?: 0
            }

            Log.d("DIARY_LIST", "Iniciando solicitud GET para obtener diarios del paciente")
            Log.d("DIARY_LIST", "ID del paciente enviado: $idPatient")

            try {
                val response = RetrofitClient.ApiServerUsers.getDiaryList(idPatient)

                Log.d("DIARY_LIST", "----- RESPUESTA DEL SERVIDOR -----")
                Log.d("DIARY_LIST", "Código HTTP: ${response.code()}")
                Log.d("DIARY_LIST", "Mensaje: ${response.message()}")
                Log.d("DIARY_LIST", "Headers: ${response.headers()}")

                val errorBodyString = response.errorBody()?.string()
                if (!errorBodyString.isNullOrEmpty()) {
                    Log.e("DIARY_LIST", "Cuerpo de error recibido:")
                    Log.e("DIARY_LIST", errorBodyString)
                }

                if (response.isSuccessful) {
                    val data = response.body() ?: emptyList()
                    Log.d("DIARY_LIST", "Cantidad de diarios recibidos: ${data.size}")
                    _diaryList.value = data
                    Log.d("DIARY_LIST", "Diarios cargados correctamente en el ViewModel")
                    onResponse(true)
                } else {
                    Log.e("DIARY_LIST", "Error HTTP no exitoso. Código: ${response.code()}")
                    onResponse(false)
                }

            } catch (e: Exception) {
                Log.e("DIARY_LIST", "Excepción al obtener la lista de diarios:")
                Log.e("DIARY_LIST", e.toString())
                e.printStackTrace()
                onResponse(false)
            }

            Log.d("DIARY_LIST", "Finalizó ejecución de getDiaryList()")
        }
    }

    fun createNewDiary(newDiary: DiaryInsertModel) {
        Log.d("DiaryViewModel", "📘 Iniciando creación de diario...")

        _diaryState.value = DiaryState.Loading

        viewModelScope.launch {
            try {
                Log.d("DiaryViewModel", "📤 Enviando diario al servidor: $newDiary")

                val response = RetrofitClient.ApiServerUsers.createNewDiary(newDiary)

                Log.d("DiaryViewModel", "📥 Respuesta recibida. Código HTTP: ${response.code()}")

                if (response.isSuccessful) {
                    val data = response.body()

                    Log.d("DiaryViewModel", "✅ Diario creado correctamente: $data")

                    _diaryResponse.value = data
                    _diaryState.value = DiaryState.Success("Interpretación completada")
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e(
                        "DiaryViewModel",
                        "❌ Error del servidor. Código: ${response.code()}, Body: $errorBody"
                    )

                    _diaryState.value = DiaryState.Error("Error al enviar diario")
                }

            } catch (e: Exception) {
                Log.e("DiaryViewModel", "🔥 Excepción al enviar diario: ${e.message}", e)
                _diaryState.value = DiaryState.Error("Error al enviar diario")
            }
        }
    }
}