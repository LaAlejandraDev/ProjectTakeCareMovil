package com.example.takecare.ui.screens.psycologist

import android.util.Log
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
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.data.models.Insert.CreateChat
import com.example.takecare.data.models.Insert.DateModelCreate
import com.example.takecare.ui.Utils.formatLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime

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
    val horario = remember { mutableStateOf("7:00 AM") }

    val patientId = psycologistViewModel.patientId.collectAsState().value

    fun openAgendaSheet(date: String) {
        selectedDate.value = date
        openSheet.value = true
    }

    fun createNewDate(workDay: PsycologistWorkDaysAllData) {
        if (psycologistId != null) {

            val (startDateParsed, endDateParsed) =
                generarFechaConHorario(workDay.date, horario.value)

            val newDate = DateModelCreate(
                id = 0,
                psycologistId = psycologistId,
                patientId = patientId,
                workDayId = workDay.id,
                startDate = startDateParsed.toString(),
                endDate = endDateParsed.toString(),
                status = "Creado",
                reason = motivo.value,
                location = ubicacion.value
            )

            Log.d("NEW_DATE_MODEL", newDate.toString())

            val newChat = CreateChat(
                psycologistId,
                patientId,
            )

            psycologistViewModel.createNewDate(newDate) { success, response ->
                if (success) {
                    alertTitle.value = "Éxito"
                    alertContent.value = response
                    psycologistViewModel.createNewChat(newChat) { success ->
                        Log.d("NEW_CHAT", "EL ESTATUS FUE: $success")
                    }
                } else {
                    alertTitle.value = "Error"
                    alertContent.value = response
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
                    horarioSeleccionado = horario.value,
                    onHorarioSeleccionadoChange = { horario.value = it },
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
    horarioSeleccionado: String,
    onHorarioSeleccionadoChange: (String) -> Unit,
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

        // MOTIVO
        OutlinedTextField(
            value = motivo,
            onValueChange = onMotivoChange,
            label = { Text("Motivo de la cita") },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 100.dp)
        )

        // UBICACIÓN
        var expandedUbicacion by remember { mutableStateOf(false) }
        val opcionesUbicacion = listOf("Presencial", "Virtual")

        val horarios = remember { generarHorariosDosHoras() }
        var expandedHorario by remember { mutableStateOf(false) }

        Text(
            "Lugar de la cita",
            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
        )

        Box {
            OutlinedButton(
                onClick = { expandedUbicacion = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(ubicacion)
            }

            DropdownMenu(
                expanded = expandedUbicacion,
                onDismissRequest = { expandedUbicacion = false }
            ) {
                opcionesUbicacion.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onUbicacionChange(opcion)
                            expandedUbicacion = false
                        }
                    )
                }
            }
        }

        Text("Horario", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))

        Box {
            OutlinedButton(
                onClick = { expandedHorario = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(horarioSeleccionado)
            }

            DropdownMenu(
                expanded = expandedHorario,
                onDismissRequest = { expandedHorario = false }
            ) {
                horarios.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onHorarioSeleccionadoChange(opcion)
                            expandedHorario = false
                        }
                    )
                }
            }
        }

        // BOTONES
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
    val workDayDate = LocalDateTime.parse(data.date)
    val today = LocalDateTime.now()
    val isAvailable = workDayDate.isBefore(today)

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

            if (!isAvailable) {
                IconButton(
                    onClick = { if (disponible) onNewDate() }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Agendar"
                    )
                }
            } else {
                Text(
                    text = "No disponible"
                )
            }
        }
    }
}

private fun generarFechaConHorario(
    fecha: String,
    rango: String
): Pair<LocalDateTime, LocalDateTime> {

    val (horaInicioStr, horaFinStr) = rango.split(" - ")

    val date = LocalDate.parse(fecha.substring(0, 10)) // yyyy-MM-dd

    val horaInicio = convertirHora12a24(horaInicioStr)
    val horaFin = convertirHora12a24(horaFinStr)

    val inicio = date.atTime(horaInicio, 0)
    val fin = date.atTime(horaFin, 0)

    return Pair(inicio, fin)
}

private fun convertirHora12a24(hora: String): Int {
    val partes = hora.split(" ")
    val horaNum = partes[0].replace(":00", "").toInt()
    val periodo = partes[1]

    return when (periodo) {
        "AM" -> if (horaNum == 12) 0 else horaNum
        "PM" -> if (horaNum == 12) 12 else horaNum + 12
        else -> horaNum
    }
}

fun generarHorariosDosHoras(): List<String> {
    val horarios = mutableListOf<String>()

    // 7 AM a 3 PM -> intervalos de 2 horas
    val startHours = listOf(7, 9, 11, 13) // 1 PM = 13 hrs

    for (start in startHours) {
        val end = start + 2

        val startLabel = formatHour(start)
        val endLabel = formatHour(end)

        horarios.add("$startLabel - $endLabel")
    }

    return horarios
}

private fun formatHour(hour: Int): String {
    val period = if (hour < 12) "AM" else "PM"
    val hour12 = when {
        hour == 0 -> 12
        hour > 12 -> hour - 12
        else -> hour
    }
    return String.format("%d:00 %s", hour12, period)
}

