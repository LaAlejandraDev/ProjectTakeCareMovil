package com.example.takecare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.takecare.ui.screens.home.HomeScreen
import com.example.takecare.ui.screens.login.LoginScreen
import com.example.takecare.ui.screens.login.LoginViewModel
import com.example.takecare.ui.screens.register.RegisterScreen
import com.example.takecare.ui.screens.visualhelp.LoadingScreen

@Composable
fun AppNavGraph(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    val context = LocalContext.current
    val isLoginWaiting = loginViewModel.isVerifyingSession.collectAsState().value
    val loginSuccess by loginViewModel.loginSuccess

    LaunchedEffect(loginSuccess) {
        loginViewModel.verifyUserLogged(context)
    }

    val startDestination = when {
        isLoginWaiting -> Routes.Loading.route
        loginSuccess -> Routes.Home.route
        else -> Routes.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen()
        }
        composable(Routes.Loading.route) {
            LoadingScreen(
                message = "Iniciando...",
                onLoadingHandler = {
                    if (loginSuccess) {
                        navController.navigate(Routes.Home.route) {
                            popUpTo(0)
                        }
                    } else {
                        navController.navigate(Routes.Login.route) {
                            popUpTo(0)
                        }
                    }
                }
            )
        }
    }
}