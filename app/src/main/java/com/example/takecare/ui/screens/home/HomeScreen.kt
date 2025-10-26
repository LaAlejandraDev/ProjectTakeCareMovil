package com.example.takecare.ui.screens.home

import BottomTabBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.takecare.ui.components.ButtonSize
import com.example.takecare.ui.components.FloatingPostButton
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.ForumCreatePost
import com.example.takecare.ui.screens.forum.ForumDetailsPost
import com.example.takecare.ui.screens.forum.ForumScreen
import com.example.takecare.ui.screens.forum.ForumViewModel

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route ?: HomeRoutes.Home.route

    val forumViewModel : ForumViewModel = viewModel()

    Scaffold (
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        bottomBar = {
            BottomTabBar(navController, currentRoute = currentRoute)
        },
        
        floatingActionButton = {
            if (currentRoute == HomeRoutes.Home.route) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FloatingPostButton(navController, buttonSize = ButtonSize.SMALL) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Crear post",
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoutes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HomeRoutes.Home.route) { ForumScreen(forumViewModel, navController) }
            composable(HomeRoutes.Messages.route) { MessagesScreen() }
            composable(HomeRoutes.Diary.route) { DiaryScreen() }
            composable(HomeRoutes.Profile.route) { ProfileScreen() }
            composable(HomeRoutes.CreatePost.route) { ForumCreatePost() }
            composable(HomeRoutes.OpenPost.route) { ForumDetailsPost(forumViewModel) }
        }
    }
}

@Composable
fun MessagesScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Mensajes")
    }
}

@Composable
fun DiaryScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Mi Diario")
    }
}

@Composable
fun ProfileScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Perfil")
    }
}
