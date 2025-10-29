package com.example.takecare.ui.screens.forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.ui.screens.forum.components.OpenPost
import com.example.takecare.ui.screens.forum.components.OpenPostActions
import com.example.takecare.ui.screens.forum.components.OpenPostAvatarHeader
import com.example.takecare.ui.screens.forum.components.OpenPostBody
import com.example.takecare.ui.screens.forum.components.OpenPostBottomBar
import com.example.takecare.ui.screens.forum.components.OpenPostComments
import com.example.takecare.ui.screens.forum.components.OpenPostHeader

@Composable
fun ForumDetailsPost(forumViewModel: ForumViewModel, rootNavController: NavController) {
    val openPost by forumViewModel.openPost.collectAsState()

    Scaffold(
        bottomBar = {
            OpenPostBottomBar()
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            if (openPost != null) {
                OpenPost (modifier = Modifier.fillMaxSize().padding(12.dp)){
                    OpenPostHeader(
                        rootNavController = rootNavController
                    )
                    OpenPostBody(openPost)
                    OpenPostActions(openPost)
                    OpenPostAvatarHeader(openPost)
                    OpenPostComments(openPost)
                }
            } else {
                ErrorPost()
            }
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