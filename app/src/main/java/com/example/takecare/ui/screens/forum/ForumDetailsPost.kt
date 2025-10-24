package com.example.takecare.ui.screens.forum

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.takecare.data.models.Post

@Composable
fun ForumDetailsPost(openPost: Post) {
    Text("Post abierto: " + openPost.title)
}