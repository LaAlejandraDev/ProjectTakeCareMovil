package com.example.takecare.ui.screens.forum

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.takecare.data.models.Post
import com.example.takecare.ui.screens.forum.components.PostCard

@Composable
fun ForumDetailsPost(forumViewModel: ForumViewModel) {
    val openPost = forumViewModel.posts.value.first()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        if (openPost != emptyList<Post>()) {
            PostCard(postData = openPost, isExpanded = true)
        } else {
            ErrorPost()
        }
    }
}

@Composable
fun ErrorPost() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "404",
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "No se pudo abrir la publicación",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "La publicación que intentaste abrir ha sido eliminada o no está disponible temporalmente.",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}