package com.example.takecare.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.takecare.ui.screens.forum.components.Avatar
import com.example.takecare.ui.screens.profile.components.TextFieldUser

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val patient = profileViewModel.selectedPatient.collectAsState().value
    val isLoading = profileViewModel.isLoading.collectAsState().value

    LaunchedEffect(Unit) {
        profileViewModel.getPatient(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (patient != null) {
            val user = patient.user

            Avatar(
                initials = user?.name?.firstOrNull()?.toString() ?: "?",
                size = 60.dp
            )

            Text(
                "${user?.name ?: ""} ${user?.firstLastName ?: ""}",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            // Campos personales
            TextFieldUser(
                value = user?.name ?: "",
                onValueChange = {},
                label = "Nombre"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextFieldUser(
                    modifier = Modifier.weight(1f),
                    value = user?.firstLastName ?: "",
                    onValueChange = {},
                    label = "Apellido Paterno"
                )
                TextFieldUser(
                    modifier = Modifier.weight(1f),
                    value = user?.secondLastName ?: "",
                    onValueChange = {},
                    label = "Apellido Materno"
                )
            }

            TextFieldUser(
                value = user?.gender ?: "",
                onValueChange = {},
                label = "Género"
            )

            TextFieldUser(
                value = user?.email ?: "",
                onValueChange = {},
                label = "Correo",
                type = KeyboardType.Email
            )

            TextFieldUser(
                value = user?.phone ?: "",
                onValueChange = {},
                label = "Teléfono",
                type = KeyboardType.Phone
            )

            TextFieldUser(
                value = patient.city ?: "",
                onValueChange = {},
                label = "Ciudad"
            )

            TextFieldUser(
                value = patient.maritalStatus ?: "",
                onValueChange = {},
                label = "Estado Civil"
            )

            TextFieldUser(
                value = patient.medicalBackground ?: "",
                onValueChange = {},
                label = "Antecedentes Médicos"
            )

            TextFieldUser(
                value = patient.emergencyContact ?: "",
                onValueChange = {},
                label = "Contacto de Emergencia"
            )
        } else {
            Text("No se encontraron datos del paciente", color = MaterialTheme.colorScheme.error)
        }
    }
}