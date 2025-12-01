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

@Composable
fun AppNavGraph(navController: NavHostController, loginViewModel: LoginViewModel = viewModel()) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController, loginViewModel)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen(navController)
        }
    }
}