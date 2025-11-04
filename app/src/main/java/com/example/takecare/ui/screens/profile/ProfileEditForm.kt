package com.example.takecare.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.takecare.data.models.PatientModel
import com.example.takecare.data.models.User
import com.example.takecare.ui.screens.forum.components.Avatar
import com.example.takecare.ui.screens.profile.components.SelectField
import com.example.takecare.ui.screens.profile.components.TextFieldUser

@Composable
fun ProfileEditForm(
    patient: PatientModel?,
    onSave: (PatientModel) -> Unit
) {
    if (patient == null) {
        Text("No se encontraron datos del paciente", color = MaterialTheme.colorScheme.error)
        return
    }

    var name by remember { mutableStateOf(patient.user?.name ?: "") }
    var firstLastName by remember { mutableStateOf(patient.user?.firstLastName ?: "") }
    var secondLastName by remember { mutableStateOf(patient.user?.secondLastName ?: "") }
    var gender by remember { mutableStateOf(patient.user?.gender ?: "") }
    var email by remember { mutableStateOf(patient.user?.email ?: "") }
    var phone by remember { mutableStateOf(patient.user?.phone ?: "") }

    var city by remember { mutableStateOf(patient.city ?: "") }
    var maritalStatus by remember { mutableStateOf(patient.maritalStatus ?: "") }
    var medicalBackground by remember { mutableStateOf(patient.medicalBackground ?: "") }
    var emergencyContact by remember { mutableStateOf(patient.emergencyContact ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar(
            initials = name.firstOrNull()?.toString() ?: "?",
            size = 60.dp
        )

        Text(
            "$name $firstLastName",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        TextFieldUser(value = name, onValueChange = { name = it }, label = "Nombre")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextFieldUser(
                modifier = Modifier.weight(1f),
                value = firstLastName,
                onValueChange = { firstLastName = it },
                label = "Apellido Paterno"
            )
            TextFieldUser(
                modifier = Modifier.weight(1f),
                value = secondLastName,
                onValueChange = { secondLastName = it },
                label = "Apellido Materno"
            )
        }

        SelectField(
            label = "Género",
            options = listOf("Seleccionar...", "Femenino", "Masculino", "No binario"),
            selectedOption = gender.ifEmpty { "Seleccionar..." },
            onOptionSelected = { if (it != "Seleccionar...") gender = it }
        )

        TextFieldUser(value = email, onValueChange = { email = it }, label = "Correo", type = KeyboardType.Email)
        TextFieldUser(value = phone, onValueChange = { phone = it }, label = "Teléfono", type = KeyboardType.Phone)
        TextFieldUser(value = city, onValueChange = { city = it }, label = "Ciudad")
        TextFieldUser(value = maritalStatus, onValueChange = { maritalStatus = it }, label = "Estado Civil")
        TextFieldUser(value = medicalBackground, onValueChange = { medicalBackground = it }, label = "Antecedentes Médicos")
        TextFieldUser(value = emergencyContact, onValueChange = { emergencyContact = it }, label = "Contacto de Emergencia")

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            val updatedPatient = patient.copy(
                city = city,
                maritalStatus = maritalStatus,
                medicalBackground = medicalBackground,
                emergencyContact = emergencyContact,
                user = patient.user?.copy(
                    name = name,
                    firstLastName = firstLastName,
                    secondLastName = secondLastName,
                    gender = gender,
                    email = email,
                    phone = phone
                ) ?: User(
                    id = patient.userId ?: 0,
                    name = name,
                    firstLastName = firstLastName,
                    secondLastName = secondLastName,
                    gender = gender,
                    email = email,
                    phone = phone
                )
            )
            onSave(updatedPatient)
        }) {
            Text("Guardar cambios")
        }
    }
}