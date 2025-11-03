package com.example.takecare.ui.screens.forum

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.components.PostCard
import com.example.takecare.ui.screens.forum.components.SearchBarComponent

@Composable
fun ForumScreen(viewModel: ForumViewModel, navController: NavController) {
    var text by remember { mutableStateOf("") }

    val context = LocalContext.current
    val posts by viewModel.posts.collectAsState()

    val filteredPosts = posts.filter {
        it.title.contains(text, ignoreCase = true)
    }

    LaunchedEffect(Unit) {
        viewModel.getUserId(context)
    }

    LaunchedEffect(Unit) {
        viewModel.getAllPost()
    }

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                SearchBarComponent(
                    query = text,
                    onQueryChange = {
                        text = it
                    }
                )
            }

            items(filteredPosts) { item ->
                PostCard(
                    postData = item,
                    onViewClick = {
                        viewModel.openPostSelected(item.id)
                        navController.navigate(HomeRoutes.OpenPost.route)
                    }
                )
            }
        }
    }
}