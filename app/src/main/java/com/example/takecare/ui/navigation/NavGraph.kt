package com.example.takecare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.takecare.ui.screens.home.HomeScreen
import com.example.takecare.ui.screens.login.LoginScreen
import com.example.takecare.ui.screens.register.RegisterScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        //startDestination = Routes.Login.route
        startDestination = HomeRoutes.Home.route
    ) {
        composable(Routes.Login.route) {
            LoginScreen(navController)
        }
        composable(Routes.Register.route) {
            RegisterScreen(navController)
        }
        composable(Routes.Home.route) {
            HomeScreen()
        }
    }
}