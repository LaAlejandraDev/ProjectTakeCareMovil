package com.example.takecare.ui.screens.profile.sections

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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.composeunstyled.Text
import com.example.takecare.data.models.AllData.DiaryAllDataModel
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.data.models.Insert.RatingModelCreate
import com.example.takecare.ui.components.DialogSimple
import com.example.takecare.ui.screens.profile.ProfileViewModel
import com.example.takecare.ui.screens.profile.components.DialogValue
import java.time.LocalDate

@Composable
fun UserDatesListSection(
    patientDatesList: List<DateModelCreate> = emptyList(),
    diaryList: List<DiaryAllDataModel> = emptyList(),
    profileViewModel: ProfileViewModel,
    onLogOut: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val pendingDates = patientDatesList.filter { !it.isStartDateExpired() }
    val finishedDates = patientDatesList.filter { it.isStartDateExpired() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        profileViewModel.selectUser(context)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Pendientes",
                    )
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Terminadas",
                    )
                }
            )

            Tab(
                selected = selectedTab == 2,
                onClick = { selectedTab = 2 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Diario",
                    )
                }
            )

            Tab(
                selected = selectedTab == 3,
                onClick = { selectedTab = 3 },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Configuracion",
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        when (selectedTab) {
            0 -> {
                if (pendingDates.isEmpty()) {
                    Text(
                        "No tienes citas pendientes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    DateListSection(patientDatesList = pendingDates)
                }
            }

            1 -> {
                if (finishedDates.isEmpty()) {
                    Text(
                        "No tienes citas terminadas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    DateListSectionFinished(
                        patientDatesList = finishedDates,
                        profileViewModel = profileViewModel
                    )
                }
            }

            2 -> {
                if (diaryList.isEmpty()) {
                    Text(
                        "No tienes un diario registrado.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    UserDiaryRegistryScreen(diaryList)
                }
            }

            3 -> {
                ConfigurationSection(
                    profileViewModel,
                    onLogOut
                )
            }
        }
    }
}

@Composable
private fun DateListSection(
    patientDatesList: List<DateModelCreate> = emptyList()
) {
    val today = remember { LocalDate.now() }

    val todayDates = patientDatesList.filter { date ->
        val localDate = runCatching {
            LocalDate.parse(date.startDate.substring(0, 10))
        }.getOrNull()
        localDate == today
    }

    val upcomingDates = patientDatesList.filter { date ->
        val localDate = runCatching {
            LocalDate.parse(date.startDate.substring(0, 10))
        }.getOrNull()
        localDate != null && localDate.isAfter(today)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Citas para este dia",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        if (todayDates.isEmpty()) {
            Text(
                "No tienes citas para hoy.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(todayDates) { item ->
                    DateListItem(item) {
                        // No hacer nada por ahora
                    }
                }
            }
        }
        Text(
            "Próximas citas",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (upcomingDates.isEmpty()) {
                item {
                    Text(
                        "No tienes próximas citas.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                items(upcomingDates) { item ->
                    DateListItem(item) {
                        // No hacer nada por ahora
                    }
                }
            }
        }
    }
}

@Composable
private fun DateListSectionFinished(
    patientDatesList: List<DateModelCreate> = emptyList(),
    profileViewModel: ProfileViewModel
) {
    var showDialog by remember { mutableStateOf(false) }
    var openInfoDialog by remember { mutableStateOf(false) }
    var dateSelect by remember { mutableStateOf<DateModelCreate?>(null) }
    var dialogInfoTitle by remember { mutableStateOf("") }
    var dialogInfoText by remember { mutableStateOf("") }

    fun onPressedDate(dateSelected: DateModelCreate) {
        showDialog = true
        dateSelect = dateSelected
    }

    fun sendRating(rating: Int) {
        if (rating == 0 || dateSelect == null) return

        dateSelect?.let { data ->
            val newRating = RatingModelCreate(
                cal = rating,
                psychologistId = data.psycologistId,
                dateId = data.id
            )

            profileViewModel.createNewRating(newRating) { success ->
                openInfoDialog = true
                if (success) {
                    dialogInfoTitle = "Exito"
                    dialogInfoText = "Tu valoración se envio con exito"
                } else {
                    dialogInfoTitle = "Error"
                    dialogInfoText = "Hubo un error al enviar tu valoración"
                }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                if (showDialog) {
                    DialogValue(
                        onDismissRequest = { showDialog = false },
                        onConfirm = { rating ->
                            sendRating(rating)
                            showDialog = false
                        }
                    )
                }
            }

            item {
                if (openInfoDialog) {
                    DialogSimple(
                        title = dialogInfoTitle,
                        text = dialogInfoText,
                        onConfirm = { openInfoDialog = false },
                        onDismiss = { openInfoDialog = false }
                    )
                }
            }

            items(patientDatesList) { item ->
                DateListItem(item) { id ->
                    onPressedDate(item)
                    showDialog = true
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

@Composable
private fun ConfigurationSection(
    profileViewModel: ProfileViewModel,
    onLogOut: () -> Unit
) {
    val context = LocalContext.current
    var newUrl by remember { mutableStateOf("") }
    var openDialog by remember { mutableStateOf(false) }
    var titleDialog by remember { mutableStateOf("title") }
    var dialogContent by remember { mutableStateOf("Content") }

    fun updateUserImage() {
        if (!newUrl.isEmpty()) {
            profileViewModel.updateImageUrl(newUrl) { success ->
                if (success) {
                    openDialog = true
                    titleDialog = "Error"
                    dialogContent = "Error al actualizar la imagen, intentalo mas tarde"
                } else {
                    openDialog = true
                    titleDialog = "Error"
                    dialogContent = "Error al actualizar la imagen, intentalo mas tarde"
                }
            }
        } else {
            openDialog = true
            titleDialog = "Error"
            dialogContent = "No se puede dejar la url vacia"
        }
    }

    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Configuración", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))

        Column (verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Modificar foto de perfil", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            OutlinedTextField(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("URL de la foto", color = Color.Gray) },
                value = newUrl,
                onValueChange = { newUrl = it },
            )
            Button(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = { updateUserImage() }
            ) {
                Text("Modificar", color = Color.White)
            }
        }

        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                "Cerrar la sesión",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Button(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    profileViewModel.closeSession(context)
                    onLogOut()
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        }
    }

    if (openDialog) {
        DialogSimple(
            title = titleDialog,
            text = dialogContent,
            onDismiss = { openDialog = false },
            onConfirm = { openDialog = false }
        )
    }
}