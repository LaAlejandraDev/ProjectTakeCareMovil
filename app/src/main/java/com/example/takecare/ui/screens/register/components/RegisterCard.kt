package com.example.takecare.ui.screens.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.takecare.ui.components.OutlinedTextFieldComponent
import com.example.takecare.ui.navigation.Routes

@Composable
fun RegisterCard(navController: NavHostController) {
    OutlinedCard (
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                "Take Care - Registro",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextFieldComponent(
                label = "e.j Juan Pablo",
                superiorLabel = "¿Cual es tu nombre?",
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { } // No hace nada por ahora
            )
            OutlinedTextFieldComponent(
                label = "e.j correo@correo.com",
                superiorLabel = "Ingresa tu correo",
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { } // No hace nada por ahora
            )
            OutlinedTextFieldComponent(
                isPassword = true,
                label = "Ingresa tu clave",
                superiorLabel = "Ingresa tu clave",
                value = "",
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { } // No hace nada por ahora
            )
            Button(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = { } // No hace nada por ahora
            ) {
                Text("Crear Cuenta")
            }
            TextButton(
                onClick = { navController.navigate(Routes.Login.route) }
            ) {
                Text("¿Ya tienes cuenta?")
            }
        }
    }
}