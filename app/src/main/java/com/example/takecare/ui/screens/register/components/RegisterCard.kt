package com.example.takecare.ui.screens.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.takecare.data.models.RegisterUserModel
import com.example.takecare.ui.components.OutlinedTextFieldComponent
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.profile.components.DatePickerModal
import com.example.takecare.ui.screens.profile.components.SelectField
import com.example.takecare.ui.screens.register.RegisterViewModel
import kotlin.text.ifEmpty

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalDataFrame(
    name: String,
    onNameChange: (String) -> Unit,
    firstLastName: String,
    onFirstLastNameChange: (String) -> Unit,
    secondLastName: String,
    onSecondLastNameChange: (String) -> Unit,
    gender: String,
    onGenderChange: (String) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Datos personales",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "Ingresa tu información personal y presiona el botón “Siguiente” para continuar.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )

        StringTextField( // Nombre
            value = name,
            label = "Nombre",
            onValueChange = onNameChange
        )

        StringTextField( // Apellido Paterno
            value = firstLastName,
            label = "Apellido Paterno",
            onValueChange = onFirstLastNameChange
        )

        StringTextField( // Apellido Materno
            value = secondLastName,
            label = "Apellido Materno",
            onValueChange = onSecondLastNameChange
        )

        SelectField(
            label = "Género",
            options = listOf("Seleccionar...", "Femenino", "Masculino", "No binario"),
            selectedOption = gender.ifEmpty { "Seleccionar..." },
            onOptionSelected = { if (it != "Seleccionar...") onGenderChange(it) }
        )
    }

}

@Composable
fun ContactDataFrame(
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Datos de contacto",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "Proporciona tus datos de contacto y presiona el botón “Siguiente” para continuar.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        StringTextField( // Correo
            value = email,
            label = "Correo Electronico",
            onValueChange = onEmailChange,
            type = KeyboardType.Email
        )
        StringTextField( // Telefono
            value = phone,
            label = "Numero de telefono",
            onValueChange = onPhoneChange,
            type = KeyboardType.Phone
        )
        StringTextField( // Clave
            value = password,
            label = "Contraseña",
            onValueChange = onPasswordChange,
            type = KeyboardType.Password
        )
    }
}

@Composable
fun MedicalInfoDataFrame(
    diagnostic: String,
    onDiagnosticChange: (String) -> Unit,
    medicalBackground: String,
    onMedicalBgChange: (String) -> Unit,
    martialStatus: String,
    onMartialStatusChange: (String) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Información médica",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "Completa tu información médica y presiona el botón “Siguiente” para continuar.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        StringTextField( // Diagnostico
            value = diagnostic,
            label = "Diagnostico Medico",
            onValueChange = onDiagnosticChange,
        )
        StringTextField( // Antecedentes
            value = medicalBackground,
            label = "Antecedentes Medicos",
            onValueChange = onMedicalBgChange,
        )

        SelectField(
            label = "Estado Civil",
            options = listOf("Seleccionar...", "Solter@", "Casad@", "Divorciad@", "Viud@"),
            selectedOption = martialStatus.ifEmpty { "Seleccionar..." },
            onOptionSelected = { if (it != "Seleccionar...") onMartialStatusChange(it) }
        )
    }
}

@Composable
fun EmergencyDataFrame(
    city: String,
    onCityChange: (String) -> Unit,
    emergencyContact: String,
    onContactChange: (String) -> Unit
) {
    Column (
        modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()).padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Emergencia y ubicación",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            "Agrega tu información de contacto de emergencia y ubicación, luego presiona el botón “Siguiente” para continuar.",
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
        )
        StringTextField( // Ciudad
            value = city,
            label = "Ciudad",
            onValueChange = onCityChange,
        )
        StringTextField( // Contacto
            value = emergencyContact,
            label = "Contacto de Emergencia",
            onValueChange = onContactChange,
            type = KeyboardType.Phone
        )
    }
}

@Composable
fun StringTextField(value: String, label: String, onValueChange: (String) -> Unit, type: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(label)
        },
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        keyboardOptions = KeyboardOptions(keyboardType = type),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.DarkGray
        )
    )
}