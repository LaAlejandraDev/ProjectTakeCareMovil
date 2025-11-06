package com.example.takecare.ui.screens.messages

import ChatItem
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.data.models.ChatModel

@Composable
fun MessagesScreen(navController: NavController, messagesViewModel: MessagesViewModel = viewModel()) {
    val chatList = messagesViewModel.chatList.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (chatList.isNotEmpty()) {
            ChatList(navController, chatList)
        } else {
            ChatEmptyList()
        }
    }
}

@Composable
fun ChatList(navController: NavController, chatList: List<ChatModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(chatList) { chat ->
            ChatItem(
                onClickChat = {
                    navController.navigate("chat_scaffold")
                }
            )
        }
    }
}

@Composable
fun ChatEmptyList() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Aún no tienes chats",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Cuando empieces una conversación con un psicólogo, aparecerá aquí.",
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)),
            textAlign = TextAlign.Center
        )
    }
}