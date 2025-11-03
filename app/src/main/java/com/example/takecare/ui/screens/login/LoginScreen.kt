package com.example.takecare.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.login.components.LoginCard
import com.example.takecare.ui.screens.visualhelp.LoadingScreen
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val loginSuccess by loginViewModel.loginSuccess

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
            LoginCard(navController, loginViewModel)
        }
    }
}