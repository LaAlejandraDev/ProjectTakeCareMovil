package com.example.takecare.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.rememberDialogState
import com.composeunstyled.Button
import com.composeunstyled.Text
import com.example.takecare.ui.components.DialogComponent
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.login.components.LoginCard
import com.example.takecare.ui.screens.visualhelp.LoadingScreen
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val loginSuccess by loginViewModel.loginSuccess
    var showDialog by remember { mutableStateOf(false) }

    val loaderFunction: () -> Unit = {
        if (loginSuccess) {
            navController.navigate(Routes.Home.route) {
                popUpTo(0)
            }
        }
    }

    LaunchedEffect(Unit) {
        loginViewModel.verifyUserLogged(context)
    }

    LaunchedEffect(loginSuccess) {
        loaderFunction()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loginSuccess) {
            LoadingScreen("Iniciando...")
        } else {
            LoginCard(navController, loginViewModel, onLoginResponse = { success ->
                if (!success) {
                    showDialog = true
                }
            })
        }
    }
    if (showDialog) {
        DialogComponent(
            title = "Error al iniciar sesión",
            message = "No se pudo iniciar sesión. Revisa tus credenciales e intenta nuevamente.",
            confirmText = "Entendido",
            onConfirm = { showDialog = false }
        )
    }
}