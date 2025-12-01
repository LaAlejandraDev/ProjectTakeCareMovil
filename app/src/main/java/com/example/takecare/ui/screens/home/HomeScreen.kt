package com.example.takecare.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.ForumDetailsPost
import com.example.takecare.ui.screens.forum.ForumViewModel
import com.example.takecare.ui.screens.home.components.AppScaffoldNavHost
import com.example.takecare.ui.screens.messages.ChatScreen
import com.example.takecare.ui.screens.psycologist.PsycoListScreen
import com.example.takecare.ui.screens.psycologist.PsycologistInfoScreen

@Composable
fun HomeScreen(mainNavController: NavController) {
    val rootNavController = rememberNavController()

    val forumViewModel: ForumViewModel = viewModel()

    NavHost(
        navController = rootNavController,
        startDestination = "main_scaffold"
    ) {
        composable("main_scaffold") {
            AppScaffoldNavHost(rootNavController, forumViewModel, mainNavController)
        }

        composable(HomeRoutes.OpenPost.route) {
            ForumDetailsPost(forumViewModel, rootNavController)
        }

        composable(
            route = "chat_scaffold/{chatId}",
            arguments = listOf(
                navArgument("chatId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            ChatScreen(navController = rootNavController, chatId = chatId)
        }

        composable("psyco_list") {
            PsycoListScreen(rootNavController)
        }

        composable(
            route = "psyco_info/{psycoId}/{name}",
            arguments = listOf(
                navArgument("psycoId") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val psycoId = backStackEntry.arguments?.getInt("psycoId")
            val name = backStackEntry.arguments?.getString("name")
            PsycologistInfoScreen(psycologistId = psycoId, psycologistName = name)
        }
    }
}
