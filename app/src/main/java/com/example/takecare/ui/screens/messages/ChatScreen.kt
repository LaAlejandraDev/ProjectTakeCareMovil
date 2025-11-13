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
    val chatInfo by chatViewModel.chatInfo.collectAsState()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        chatViewModel.connectSignalR()
        chatId?.toIntOrNull()?.let {
            chatViewModel.getChatInfo(it)
            chatViewModel.getChatData(it)
            chatViewModel.getChatMessages(it)
        }
    }

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.isNotEmpty()) {
            coroutineScope.launch {
                delay(100)
                listState.animateScrollToItem(chatMessages.size - 1)
            }
        }
    }

    fun sendMessage() {
        if (newMessage.isBlank() || chatInfo == null || chatId == null) return

        val idSender = chatInfo?.idPatient

        val messageToSend = MessageModelCreate(
            chatId = chatId.toInt(),
            senderUserId = idSender!!,
            message = newMessage,
            isRead = true
        )

        Log.i("CHAT_SEND", "Intentando enviar mensaje: $messageToSend")

        chatViewModel.createNewMessage(messageToSend) { success ->
            if (success) {
                Log.i("CHAT_SEND", "Mensaje creado en la base de datos. Enviando por SignalR...")
                chatViewModel.sendSignalRMessage(messageToSend)
            } else {
                Log.e("CHAT_SEND", "Error al crear el mensaje en la base de datos. No se enviará por SignalR.")
            }
        }

        newMessage = ""
    }

    Scaffold(
        topBar = {
            chatInfo?.let {
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
                            owner = msg.senderId == chatInfo?.idPatient
                        )
                    }
                }
            }
        }
    }
}