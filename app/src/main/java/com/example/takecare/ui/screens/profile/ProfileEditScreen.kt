package com.example.takecare.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = viewModel()) {
    val context = LocalContext.current
    val patient = profileViewModel.selectedPatient.collectAsState().value
    val isLoading = profileViewModel.isLoading.collectAsState().value

    LaunchedEffect(Unit) {
        profileViewModel.getPatient(context)
    }

    if (isLoading) {
        Column (
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        ProfileEditForm(patient = patient) { updatedPatient ->
            profileViewModel.updatePatient(context, updatedPatient)
        }
    }
}