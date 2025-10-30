package com.example.takecare.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.ForumDetailsPost
import com.example.takecare.ui.screens.forum.ForumViewModel
import com.example.takecare.ui.screens.home.components.AppScaffoldNavHost

@Composable
fun HomeScreen() {
    val rootNavController = rememberNavController()

    val forumViewModel : ForumViewModel = viewModel()

    NavHost(
        navController = rootNavController,
        startDestination = "main_scaffold"
    ) {
        composable("main_scaffold") {
            AppScaffoldNavHost(rootNavController, forumViewModel)
        }

        composable(HomeRoutes.OpenPost.route) {
            ForumDetailsPost(forumViewModel, rootNavController)
        }
    }
}
