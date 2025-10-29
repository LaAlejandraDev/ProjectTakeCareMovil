package com.example.takecare.ui.screens.register.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.takecare.data.models.RegisterUserModel
import com.example.takecare.ui.components.OutlinedTextFieldComponent
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.register.RegisterViewModel

@Composable
fun RegisterCard(navController: NavHostController, registerViewModel: RegisterViewModel = viewModel()) {
    var name by remember { mutableStateOf("") }
    var fatherLastName by remember { mutableStateOf("") }
    var motherLastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    fun createNewUser() {
        val newUser = RegisterUserModel(
            name = name,
            firstLastName = fatherLastName,
            secondLastName = motherLastName,
            email = email,
            phone = phoneNumber,
            password = password,
            role = 1
        )

        registerViewModel.createNewUser(newUser)
    }

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
                value = name,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { name = it}
            )
            OutlinedTextFieldComponent(
                label = "e.j Juan Pablo",
                superiorLabel = "¿Cual es tu apellido paterno?",
                value = fatherLastName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { fatherLastName = it}
            )
            OutlinedTextFieldComponent(
                label = "e.j Juan Pablo",
                superiorLabel = "¿Cual es tu apellido materno?",
                value = motherLastName,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { motherLastName = it}
            )
            OutlinedTextFieldComponent(
                label = "e.j correo@correo.com",
                superiorLabel = "Ingresa tu correo",
                value = email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email = it }
            )
            OutlinedTextFieldComponent(
                label = "e.j Juan Pablo",
                superiorLabel = "¿Cual es tu telefono?",
                value = phoneNumber,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { phoneNumber = it }
            )
            OutlinedTextFieldComponent(
                isPassword = true,
                label = "Ingresa tu clave",
                superiorLabel = "Ingresa tu clave",
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it }
            )
            Button(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    createNewUser()
                }
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