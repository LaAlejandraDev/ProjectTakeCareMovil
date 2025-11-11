package com.example.takecare.ui.screens.messages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.data.models.AllData.MessageAllDataModel
import com.example.takecare.data.models.Insert.MessageModelCreate
import com.example.takecare.ui.screens.forum.components.OpenPostBottomBar
import com.example.takecare.ui.screens.messages.components.MessageComponent
import com.example.takecare.ui.screens.messages.components.TopBarChat
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavController,
    chatId: String?,
    chatViewModel: ChatViewModel = viewModel()
) {
    var newMessage by remember { mutableStateOf("") }
    val chatMessages by chatViewModel.chatMessagesData.collectAsState()

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val chatInfo = chatViewModel.chatInfo.collectAsState()

    val currentUserId = chatViewModel.chatData.value?.idPatient ?: -1

    LaunchedEffect(Unit) {
        chatViewModel.connectSignalR()
        chatId?.toIntOrNull()?.let {
            chatViewModel.getChatInfo(it)
            chatViewModel.getChatData(it)
            chatViewModel.getChatMessages(it)
        }
    }

    fun sendMessage() {
        if (newMessage.isBlank()) return
        chatViewModel.sendSignalRMessage(currentUserId, newMessage)

        val msg = MessageModelCreate(chatId!!.toInt(), currentUserId, newMessage, true)
        chatViewModel.createNewMessage(msg) { success ->
            if (success) {
                coroutineScope.launch {
                    delay(100)
                    listState.animateScrollToItem(chatMessages.size - 1)
                }
            }
        }
        newMessage = ""
    }

    Scaffold(
        topBar = {
            chatInfo.value?.let {
                TopBarChat(navController, it)
            }
        },
        bottomBar = {
            OpenPostBottomBar(
                onSendClick = { sendMessage() },
                onContentChange = { newMessage = it },
                commentText = newMessage
            )
        }
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(
                state = listState,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                if (chatMessages.isEmpty()) {
                    item {
                        Text(
                            "No hay mensajes aún",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    }
                } else {
                    items(chatMessages) { msg ->
                        MessageComponent(
                            message = msg.message,
                            owner = msg.senderId == currentUserId
                        )
                    }
                }
            }
        }
    }
}