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
import com.example.takecare.data.models.Insert.MessageModelCreate
import com.example.takecare.data.models.MessageTemp
import com.example.takecare.ui.screens.forum.components.OpenPostBottomBar
import com.example.takecare.ui.screens.messages.components.MessageComponent
import com.example.takecare.ui.screens.messages.components.TopBarChat
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    navController: NavController,
    chatId: String?,
    chatViewModel: ChatViewModel = viewModel()
) {
    var newMessage by remember { mutableStateOf("") }
    val messageList = remember { mutableStateListOf<MessageTemp>() }
    val chatData = chatViewModel.chatData.collectAsState().value

    val hubConnection: HubConnection = remember {
        HubConnectionBuilder.create("http://192.168.0.107:5002/chathub").build()
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val currentUser = "AppAndroid"

    fun createNewMessage() {
        if (chatId.isNullOrBlank() || chatData == null) {
            Log.w("MESSAGE_SEND", "No se pudo crear mensaje: chatId o chatData nulo.")
            return
        }

        val newMessageData = MessageModelCreate(
            chatId = chatId.toInt(),
            senderUserId = chatData.idPatient,
            message = newMessage,
            isRead = true
        )

        Log.i("MESSAGE_SEND", "Enviando mensaje: ${newMessageData.message}")
        chatViewModel.createNewMessage(newMessageData)
    }

    LaunchedEffect(chatId) {
        if (!chatId.isNullOrBlank()) {
            Log.i("CHAT_ID_GET", "ID del chat recibido: $chatId")
            try {
                chatViewModel.getChatData(chatId.toInt())
            } catch (e: Exception) {
                Log.e("CHAT_ID_ERROR", "Error al obtener chat con ID $chatId: ${e.message}")
            }
        } else {
            Log.w("CHAT_ID_NULL", "chatId es nulo o vacío, no se cargó el chat.")
        }
    }

    LaunchedEffect(Unit) {
        hubConnection.on("ReceiveMessage", { user: String, message: String ->
            Log.i("MESSAGE", "Mensaje recibido: $user -> $message")
            messageList.add(MessageTemp(user, message))
            coroutineScope.launch {
                listState.animateScrollToItem(messageList.size - 1)
            }
        }, String::class.java, String::class.java)

        try {
            hubConnection.start().blockingAwait()
            Log.i("MESSAGE", "Conectado al servidor SignalR")
        } catch (e: Exception) {
            Log.e("MESSAGE", "Error al conectar al servidor: ${e.message}")
        }
    }

    fun sendMessage() {
        if (newMessage.isNotBlank()) {
            Log.i("MESSAGE_SEND", "Enviando mensaje por SignalR: $newMessage")
            hubConnection.send("SendMessage", currentUser, newMessage)
            createNewMessage()
            newMessage = ""
        } else {
            Log.w("MESSAGE_EMPTY", "Intento de enviar mensaje vacío.")
        }
    }

    Scaffold(
        topBar = {
            chatData?.let {
                TopBarChat(navController, it)
            }
        },
        bottomBar = {
            if (chatId != null) {
                OpenPostBottomBar(
                    onSendClick = { sendMessage() },
                    onContentChange = { newMessage = it },
                    commentText = newMessage
                )
            } else {
                BottomAppBar {
                    Text(
                        "Chat no disponible",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (chatId == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    Text(
                        text = "No se encontró información del chat.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = listState
                ) {
                    if (messageList.isEmpty()) {
                        item {
                            Text(
                                "No hay mensajes aún",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        items(messageList) { msg ->
                            MessageComponent(
                                user = msg.user,
                                message = msg.content,
                                owner = msg.user == currentUser
                            )
                        }
                    }
                }
            }
        }
    }
}