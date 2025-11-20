package com.example.takecare.ui.screens.psycologist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.takecare.data.models.AllData.PsycologistWorkDaysAllData
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.ui.Utils.formatLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PsycologistInfoScreen(
    psycologistId: Int? = null,
    psycologistName: String?,
    psycologistViewModel: PsycologistViewModel = viewModel()
) {
    val context = LocalContext.current

    val openAlertDialog = remember { mutableStateOf(false) }
    val daysList = psycologistViewModel.workDays.collectAsState().value
    val alertTitle = remember { mutableStateOf("Éxito") }
    val alertContent = remember { mutableStateOf("Se ha registrado la cita con éxito") }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val openSheet = remember { mutableStateOf(false) }

    val selectedDate = remember { mutableStateOf<String?>(null) }
    val motivo = remember { mutableStateOf("") }
    val ubicacion = remember { mutableStateOf("Presencial") }

    val patientId = psycologistViewModel.patientId.collectAsState().value

    fun openAgendaSheet(date: String) {
        selectedDate.value = date
        openSheet.value = true
    }

    fun createNewDate(workDay: PsycologistWorkDaysAllData) {
        if (psycologistId != null) {
            val newDate = DateModelCreate(
                id = 0,
                psycologistId = psycologistId,
                patientId = patientId,
                workDayId = workDay.id,
                startDate = workDay.date,
                endDate = workDay.date,
                status = "Creado",
                reason = motivo.value,
                location = ubicacion.value
            )

            psycologistViewModel.createNewDate(newDate) { success ->

                if (success) {
                    alertTitle.value = "Éxito"
                    alertContent.value = "La cita fue agendada correctamente."
                } else {
                    alertTitle.value = "Error"
                    alertContent.value = "No se pudo registrar la cita. Inténtalo más tarde."
                }

                openAlertDialog.value = true
                openSheet.value = false
                motivo.value = ""
                ubicacion.value = "Presencial"
            }
        }
    }

    LaunchedEffect(psycologistId) {
        if (psycologistId != null) {
            psycologistViewModel.getWorkDays(psycologistId) { success ->
                if (!success) openAlertDialog.value = true
            }
        }
    }

    LaunchedEffect(Unit) {
        psycologistViewModel.getUserData(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(psycologistName ?: "Psicólogo") }
            )
        }
    ) { innerPadding ->

        if (openSheet.value) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { openSheet.value = false }
            ) {
                AgendaSheetContent(
                    motivo = motivo.value,
                    onMotivoChange = { motivo.value = it },
                    ubicacion = ubicacion.value,
                    onUbicacionChange = { ubicacion.value = it },
                    onCancelar = { openSheet.value = false },
                    onAgregar = {
                        selectedDate.value?.let { dateValue ->
                            val workDay = daysList.find { it.date == dateValue }
                            if (workDay != null) {
                                createNewDate(workDay)
                            }
                        }
                    }
                )
            }
        }

        Column(modifier = Modifier.padding(innerPadding)) {

            when {
                openAlertDialog.value -> {
                    AlertDialogError(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = alertTitle.value,
                        dialogText = alertContent.value
                    )
                }

                daysList.isEmpty() -> {
                    Text(
                        "No hay días laborales disponibles",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(daysList.size) { index ->
                            val item = daysList[index]
                            DayItem(
                                data = item,
                                onNewDate = {
                                    openAgendaSheet(item.date)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AgendaSheetContent(
    motivo: String,
    onMotivoChange: (String) -> Unit,
    ubicacion: String,
    onUbicacionChange: (String) -> Unit,
    onCancelar: () -> Unit,
    onAgregar: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Agendar cita",
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
        )

        OutlinedTextField(
            value = motivo,
            onValueChange = onMotivoChange,
            label = { Text("Motivo de la cita") },
            modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp)
        )

        var expanded by remember { mutableStateOf(false) }
        val opciones = listOf("Presencial", "Virtual")

        Text("Lugar de la cita", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
        Box {
            OutlinedButton(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(ubicacion)
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onUbicacionChange(opcion)
                            expanded = false
                        }
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier.weight(1f)
            ) {
                Text("Cancelar")
            }

            Button(
                onClick = onAgregar,
                modifier = Modifier.weight(1f)
            ) {
                Text("Agregar")
            }
        }
    }
}


@Composable
fun DayItem(data: PsycologistWorkDaysAllData, onNewDate: () -> Unit) {
    val disponible = data.currentDates < data.max

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = formatLocalDateTime(data.date),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )

                Text(
                    text = if (disponible) "Disponibilidad" else "Sin disponibilidad",
                    color = if (disponible) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }

            IconButton(
                onClick = { if (disponible) onNewDate() }
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Agendar"
                )
            }
        }
    }
}
