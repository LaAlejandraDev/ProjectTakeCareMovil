package com.example.takecare.ui.screens.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.data.models.Post
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.forum.components.PostCard
import com.example.takecare.ui.screens.forum.components.SearchBarComponent

@Composable
fun ForumScreen(viewModel: ForumViewModel, navController: NavController) {
    var text by remember { mutableStateOf("") }

    val posts = emptyList<Post>()

    val filteredPosts = posts.filter {
        it.title.contains(text, ignoreCase = true)
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
                    postData = item!!,
                    onViewClick = {
                        //viewModel.selectPost(item)
                        navController.navigate(HomeRoutes.OpenPost.route)
                    }
                )
            }
        }

    }
}