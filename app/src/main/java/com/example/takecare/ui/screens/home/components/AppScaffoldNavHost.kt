package com.example.takecare.ui.screens.home.components

import BottomTabBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.takecare.ui.Utils.UIEvent
import com.example.takecare.ui.components.ButtonSize
import com.example.takecare.ui.components.FloatingPostButton
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.ForumCreatePost
import com.example.takecare.ui.screens.forum.ForumScreen
import com.example.takecare.ui.screens.forum.ForumViewModel
import com.example.takecare.ui.screens.messages.MessagesScreen
import com.example.takecare.ui.screens.profile.ProfileScreen
import kotlinx.coroutines.coroutineScope

@Composable
fun AppScaffoldNavHost(rootNavController : NavController, forumViewModel: ForumViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        forumViewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.Showsnackbar -> {
                    coroutineScope {
                        snackbarHostState.showSnackbar(
                            message = event.message,
                            actionLabel = "OK"
                        )
                    }
                }
            }
        }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        topBar = {
            if (currentRoute == HomeRoutes.Messages.route) {
                TopBarMessages(
                    onNewChat = {
                        rootNavController.navigate("psyco_list")
                    }
                )
            }
        },
        bottomBar = {
            BottomTabBar(navController, currentRoute = currentRoute)
        },
        floatingActionButton = {
            if (currentRoute == HomeRoutes.Home.route) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FloatingPostButton(navController, buttonSize = ButtonSize.MEDIUM) {
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
            composable(HomeRoutes.Home.route) { ForumScreen(forumViewModel, rootNavController) }
            composable(HomeRoutes.Messages.route) { MessagesScreen(rootNavController) }
            //composable(HomeRoutes.Diary.route) { DiaryScreen() }
            composable(HomeRoutes.Profile.route) { ProfileScreen(rootNavController) }
            composable(HomeRoutes.CreatePost.route) { ForumCreatePost() }
        }
    }
}