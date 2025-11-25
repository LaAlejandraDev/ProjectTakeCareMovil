package com.example.takecare.ui.screens.dairy.sections

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.example.takecare.ui.screens.dairy.DiaryState

@Composable
fun DiaryConfirmationSection(
    diaryState: DiaryState,
    onConfirm: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedContent(
            targetState = diaryState,
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) { state ->
            when (state) {
                is DiaryState.Loading -> LoadingResponse()
                is DiaryState.Success -> LoadingResponseSuccess(onConfirm)
                is DiaryState.Error -> LoadingResponseError()
                else -> Unit
            }
        }
    }
}

@Composable
fun LoadingResponse() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CircularProgressIndicator()
        Text("Procesando tu entrada...", style = MaterialTheme.typography.bodyMedium)
    }
}


@Composable
fun LoadingResponseSuccess(onConfirm: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color(0xFF4CAF50),
            modifier = Modifier.size(48.dp)
        )

        Text("¡Tu diario fue guardado exitosamente!",
            style = MaterialTheme.typography.titleMedium)

        TextButton(onClick = onConfirm) {
            Text("Continuar")
        }
    }
}

@Composable
fun LoadingResponseError() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = Color(0xFFD32F2F),
            modifier = Modifier.size(48.dp)
        )

        Text("Ocurrió un error al procesar tu diario.",
            style = MaterialTheme.typography.titleMedium)

        Text(
            "Por favor intenta nuevamente.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}