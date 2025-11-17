package com.example.takecare.ui.screens.psycologist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.data.models.AllData.PsycologistAllData
import com.example.takecare.ui.screens.psycologist.components.TopBarPsyco

@Composable
fun PsycoListScreen(
    rootNavController: NavController,
    psycologistViewModel: PsycologistViewModel = viewModel()
) {
    val psycologistList = psycologistViewModel.psycologistList.collectAsState().value
    val openAlertDialog = remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        psycologistViewModel.getAllPsycologist { success ->
            isLoading = false
            if (!success) {
                openAlertDialog.value = true
            }
        }
    }

    Scaffold(
        topBar = { TopBarPsyco() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            when {
                openAlertDialog.value -> {
                    AlertDialogError(
                        onDismissRequest = { openAlertDialog.value = false },
                        onConfirmation = { openAlertDialog.value = false },
                        dialogTitle = "Error",
                        dialogText = "Error al obtener la lista de psicólogos"
                    )
                }

                isLoading -> {
                    LoadingList()
                }

                psycologistList.isEmpty() -> {
                    Text(
                        text = "No hay psicólogos disponibles",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(psycologistList.size) { index ->
                            val item = psycologistList[index]
                            PsycoListItem(
                                psycoData = item,
                                rootNavController
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingList() {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PsycoListItem(
    psycoData: PsycologistAllData,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = psycoData.speciality,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = psycoData.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Experiencia: ${psycoData.experience} años ",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = psycoData.university,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(psycoData.rating.toInt()) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star"
                    )
                }
                if (psycoData.rating % 1 != 0f) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Half Star"
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                Text(
                    text = "${psycoData.rating} (${psycoData.totalReviews} reseñas)",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Cédula: ${psycoData.profesionalInfo}",
                style = MaterialTheme.typography.labelSmall
            )

            Text(
                text = psycoData.direction,
                style = MaterialTheme.typography.labelSmall
            )

            OutlinedButton (
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                onClick = { navController.navigate("psyco_info/${psycoData.id}") }
            ) {
                Text("Reservar una cita", color = Color.Black)
            }
        }
    }
}

@Composable
fun AlertDialogError(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        }
    )
}