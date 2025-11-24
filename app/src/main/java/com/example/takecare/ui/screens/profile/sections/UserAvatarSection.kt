package com.example.takecare.ui.screens.profile.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.example.takecare.data.models.PatientModel
import com.example.takecare.ui.screens.forum.components.Avatar
import kotlin.text.firstOrNull

@Composable
fun UserAvatarSection(patient: PatientModel?) {
    val patientName = patient?.user?.name ?: ""
    val patientFullName =
        "${patient?.user?.name} ${patient?.user?.firstLastName} ${patient?.user?.secondLastName}"

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when {
            patient != null -> {
                UserHeader(patientName, patientFullName)
            }

            else -> {
                Text("Error al obtener la informacion")
            }
        }
    }
}

@Composable
private fun UserHeader(
    patientName: String,
    fullPatientName: String
) {
    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(patientName.firstOrNull()?.toString() ?: "?", size = 50.dp)
            Column {
                Text(
                    fullPatientName,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    "Paciente",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Gray
                )
            }
        }
    }
}