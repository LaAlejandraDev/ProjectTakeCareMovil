package com.example.takecare.ui.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.ui.screens.dairy.DiaryViewModel
import com.example.takecare.ui.screens.profile.sections.UserAvatarSection
import com.example.takecare.ui.screens.profile.sections.UserDatesListSection
import com.example.takecare.ui.screens.psycologist.AlertDialogError
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    rootNavController: NavController,
    profileViewModel: ProfileViewModel = viewModel(),
    diaryViewModel: DiaryViewModel = viewModel()
) {
    val context = LocalContext.current
    val patient = profileViewModel.selectedPatient.collectAsState().value
    val isLoading = profileViewModel.isLoading.collectAsState().value
    val userDateList = profileViewModel.patientDateList.collectAsState().value
    val openAlertDialog = remember { mutableStateOf(false) }
    val diaryList = diaryViewModel.diaryList.collectAsState().value

    LaunchedEffect(Unit) {
        profileViewModel.getPatient(context)
        diaryViewModel.getPatient(context)
        delay(1000)
        profileViewModel.getPatientDatesList { success ->
            if (!success) {
                openAlertDialog.value = true
            }
        }
        diaryViewModel.getDiaryList() { success ->
            if(success) {
                Log.d("LIST", "EXITO")
            } else {
                Log.e("LIST", "ERROR")
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when {
            openAlertDialog.value -> {
                AlertDialogError(
                    onConfirmation = { openAlertDialog.value = false },
                    onDismissRequest = { openAlertDialog.value = false },
                    dialogTitle = "Error",
                    dialogText = "Error al obtener las citas"
                )
            }

            isLoading -> {
                CircularProgressIndicator()
            }

            else -> {
                UserAvatarSection(patient)
                UserDatesListSection(userDateList, diaryList)
            }
        }
    }
}