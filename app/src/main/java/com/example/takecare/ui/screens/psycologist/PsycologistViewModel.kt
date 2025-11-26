package com.example.takecare.ui.screens.psycologist

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.takecare.data.client.RetrofitClient
import com.example.takecare.data.models.AllData.PsycologistAllData
import com.example.takecare.data.models.AllData.PsycologistWorkDaysAllData
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.CreateChat
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.ui.Utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PsycologistViewModel : ViewModel() {
    private val _patientId = MutableStateFlow(0)
    val patientId : StateFlow<Int> = _patientId

    private val _psycologistList = MutableStateFlow<List<PsycologistAllData>>(emptyList())
    val psycologistList: StateFlow<List<PsycologistAllData>> = _psycologistList

    private val _workDays = MutableStateFlow<List<PsycologistWorkDaysAllData>>(emptyList())
    val workDays: StateFlow<List<PsycologistWorkDaysAllData>> = _workDays

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

    fun getWorkDays(id: Int, onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.ApiServerPsycologist.getWorkDays(id)
                if (response.isSuccessful) {
                    onResponse(true)
                    val data = response.body() ?: emptyList()
                    _workDays.value = data
                } else {
                    onResponse(false)
                }
            } catch (e: Exception) {
                onResponse(false)
            }
        }
    }

    fun getUserData(context: Context) {
        viewModelScope.launch {
            try {
                val sessionManager = SessionManager(context)
                val userId = sessionManager.getUserId().firstOrNull()
                if (userId != null) {
                    val response = RetrofitClient.ApiServerUsers.getPatient(userId)
                    if (response.isSuccessful) {
                        val data = response.body()?.id ?: 0
                        _patientId.value = data
                    } else {
                        Log.e("USERDATA_ERROR", "${response.code()}")
                    }
                }
            } catch (e: Exception) {

            }
        }
    }

    fun createNewDate(date: DateModelCreate, onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("DATE_DEBUG", date.toString())

                Log.d("DATE_DEBUG", """
                ⬆️ Enviando petición para crear cita
                ├── PsycologistId: ${date.psycologistId}
                ├── PatientId: ${date.patientId}
                ├── WorkDayId: ${date.workDayId}
                ├── Fecha: ${date.startDate}
                ├── Motivo: ${date.reason}
                ├── Lugar: ${date.location}
            """.trimIndent())

                val response = RetrofitClient.ApiServerPsycologist.createNewDate(date)

                if (response.isSuccessful) {
                    Log.d("DATE_DEBUG", """
                    ✅ Cita creada exitosamente
                    ├── Código: ${response.code()}
                    └── Mensaje: ${response.message()}
                """.trimIndent())

                    onResponse(true)

                } else {
                    val errorBody = response.errorBody()?.string()

                    Log.e("DATE_ERROR", """
                    ❌ Error al crear la cita
                    ├── Código: ${response.code()}
                    ├── Mensaje: ${response.message()}
                    ├── URL: ${response.raw().request.url}
                    ├── Método: ${response.raw().request.method}
                    └── ErrorBody:
                        $errorBody
                """.trimIndent())

                    onResponse(false)
                }

            } catch (e: Exception) {
                Log.e("DATE_ERROR_SERVER", """
                💥 Excepción al conectar con el servidor
                ├── Tipo: ${e::class.java.simpleName}
                ├── Mensaje: ${e.message}
                └── Stacktrace:
            """.trimIndent())

                e.printStackTrace()

                onResponse(false)
            }
        }
    }

    fun createNewChat(chatModel: CreateChat, onResponse: (Boolean) -> Unit) {
        viewModelScope.launch {
            Log.d("CreateChat", "Iniciando creación de chat...")
            Log.d("CreateChat", "Datos enviados: $chatModel")

            try {
                val response = RetrofitClient.ApiServerPsycologist.createNewChat(chatModel)

                Log.d("CreateChat", "Código HTTP: ${response.code()}")

                if (response.isSuccessful) {
                    Log.d("CreateChat", "Chat creado correctamente: ${response.body()}")
                    onResponse(true)
                } else {
                    Log.e("CreateChat", "Error al crear chat: ${response.errorBody()?.string()}")
                    onResponse(false)
                }

            } catch (e: Exception) {
                Log.e("CreateChat", "Excepción al crear chat: ${e.localizedMessage}")
                e.printStackTrace()
                onResponse(false)
            }
        }
    }
}