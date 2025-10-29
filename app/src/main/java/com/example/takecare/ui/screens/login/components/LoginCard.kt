package com.example.takecare.ui.screens.login.components

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.takecare.ui.Utils.SessionManager
import com.example.takecare.ui.components.OutlinedTextFieldComponent
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.login.LoginViewModel
import com.example.takecare.ui.screens.login.LoginViewModelFactory

@Composable
fun LoginCard(navController: NavHostController) {

    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(sessionManager)
    )

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val loginSuccess by loginViewModel.loginSuccess

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate(Routes.Home.route) {
                popUpTo(Routes.Login.route) { inclusive = true }
            }
        }
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
                "Take Care - Iniciar Sesion",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            OutlinedTextFieldComponent(
                label = "e.j correo@correo.com",
                superiorLabel = "Ingresa tu correo",
                value = email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { email = it}
            )
            OutlinedTextFieldComponent(
                isPassword = true,
                label = "Ingresa tu clave",
                superiorLabel = "Ingresa la clave",
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { password = it }
            )
            Button(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    loginViewModel.loginUser(email, password)
                }
            ) {
                Text("Iniciar Sesion")
            }
            TextButton(
                onClick = { navController.navigate(Routes.Register.route) }
            ) {
                Text("¿No tienes cuenta?")
            }
        }
    }
}