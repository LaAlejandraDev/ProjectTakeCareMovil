package com.example.takecare.ui.screens.psycologist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsycologistInfoScreen(
    psycologistId: Int? = null
) {

    val mockName = "Dra. Mariana López"
    val mockEspecialidad = "Psicología Clínica"
    val mockDescripcion =
        "Especialista en terapia cognitivo-conductual enfocada en ansiedad, estrés laboral y depresión."
    val mockExperiencia = 6
    val mockUniversidad = "UNAM - Universidad Nacional Autónoma de México"
    val mockCedula = "1234567MX"
    val mockDireccion = "Av. Reforma 123, CDMX"
    val mockCalificacion = 4.8f
    val mockResenas = 32

    Scaffold(
        topBar = {
            TopAppBar (
                title = { Text(mockName) }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            PsycologistInfoCard(
                nombre = mockName,
                especialidad = mockEspecialidad,
                descripcion = mockDescripcion,
                experiencia = mockExperiencia,
                universidad = mockUniversidad,
                cedula = mockCedula,
                direccion = mockDireccion,
                calificacion = mockCalificacion,
                resenas = mockResenas
            )

            Button(
                onClick = { /* TODO: Navegar a calendario o reserva */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Reservar cita")
            }
        }
    }
}

@Composable
fun PsycologistInfoCard(
    nombre: String,
    especialidad: String,
    descripcion: String,
    experiencia: Int,
    universidad: String,
    cedula: String,
    direccion: String,
    calificacion: Float,
    resenas: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = nombre,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = especialidad,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Experiencia: $experiencia años",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Universidad: $universidad",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Cédula Profesional: $cedula",
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "Dirección del consultorio: $direccion",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Calificación: $calificacion ⭐ ($resenas reseñas)",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}