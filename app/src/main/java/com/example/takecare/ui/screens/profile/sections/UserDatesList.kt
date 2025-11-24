package com.example.takecare.ui.screens.profile.sections

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.ui.screens.profile.components.DialogValue

@Composable
fun UserDatesListSection(
    patientDatesList: List<DateModelCreate> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        when {
            patientDatesList.isNotEmpty() -> {
                DateListSection(patientDatesList)
            }
        }
    }
}

@Composable
private fun DateListSection(
    patientDatesList: List<DateModelCreate> = emptyList()
) {
    var showDialog by remember { mutableStateOf(false) }
    var idSelected by remember { mutableStateOf(-1) }

    fun onPressedDate(id: Int) {
        showDialog = true
        idSelected = id
        Log.d("ID_DATE", "ID: $id")
    }

    fun sendRating(rating: Int) {
        //TODO: Enviar la calificacion al psicologo
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Valora tus citas",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                when {
                    showDialog -> {
                        DialogValue(
                            onDismissRequest = { showDialog = false },
                            onConfirm = { rating ->
                                showDialog = false
                            }
                        )
                    }
                }
            }

            items(patientDatesList) { item ->
                DateListItem(item) { id ->
                    onPressedDate(id)
                }
            }
        }
    }
}

@Composable
private fun DateListItem(
    dateItem: DateModelCreate,
    onPressedDate: (Int) -> Unit
) {
    val expired = dateItem.isStartDateExpired()
    val statusColor = if (expired) Color(0xFFD9534F) else Color(0xFF5CB85C)
    val statusText = if (expired) "Expirada" else "Activa"

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .clickable {
                onPressedDate(dateItem.id)
            },
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Cita",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = dateItem.startDate.replace("T", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = statusText,
                        color = statusColor,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Lugar",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Lugar: ${dateItem.location}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (dateItem.reason.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Motivo",
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Motivo: ${dateItem.reason}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}