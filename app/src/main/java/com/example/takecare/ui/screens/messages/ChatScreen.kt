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

    val chatData = chatViewModel.chatData.collectAsState().value
    val chatMessages = chatViewModel.chatMessagesData.collectAsState().value
    val chatInfo = chatViewModel.chatInfo.collectAsState().value

    val tempMessages = remember { mutableStateListOf<MessageAllDataModel>() }

    val hubConnection: HubConnection = remember {
        HubConnectionBuilder.create("http://192.168.0.107:5002/chathub").build()
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val currentUserId = chatData?.idPatient ?: -1

    LaunchedEffect(chatMessages) {
        tempMessages.clear()
        tempMessages.addAll(chatMessages)
    }

    fun createNewMessage(onResult: (Boolean) -> Unit) {
        if (chatId.isNullOrBlank() || chatData == null) {
            Log.w("MESSAGE_SEND", "No se pudo crear mensaje: chatId o chatData nulo.")
            onResult(false)
            return
        }

        val newMessageData = MessageModelCreate(
            chatId = chatId.toInt(),
            senderUserId = chatData.idPatient,
            message = newMessage,
            isRead = true
        )

        Log.i("MESSAGE_SEND", "Enviando mensaje a la API: ${newMessageData.message}")

        chatViewModel.createNewMessage(newMessageData) { success ->
            if (success) {
                Log.i("MESSAGE_UI_UPDATE", "Mensaje agregado localmente a la lista temporal")
                onResult(true)
            } else {
                Log.e("MESSAGE_UI_ERROR", "No se pudo agregar mensaje localmente (falló el envío)")
                onResult(false)
            }
        }
    }

    LaunchedEffect(Unit) {
        if (!chatId.isNullOrBlank()) {
            Log.i("MENSAJES", "EJECUTANDO LISTA DE MENSAJES")
            chatViewModel.getChatInfo(chatId.toInt())
            chatViewModel.getChatData(chatId.toInt())
            chatViewModel.getChatMessages(chatId.toInt())
        }
    }

    LaunchedEffect(Unit) {
        hubConnection.on("ReceiveMessage", { user: String, message: String ->
            Log.i("MESSAGE_RECEIVED", "Mensaje recibido: $user -> $message")

            tempMessages.add(
                MessageAllDataModel(
                    id = -1,
                    chatId = chatId?.toInt() ?: 0,
                    senderId = user.toIntOrNull() ?: 0,
                    message = message,
                    readed = false,
                    date = ""
                )
            )

            coroutineScope.launch {
                delay(100)
                if (tempMessages.isNotEmpty()) {
                    listState.animateScrollToItem(tempMessages.size - 1)
                }
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
            hubConnection.send("SendMessage", currentUserId.toString(), newMessage)

            createNewMessage { success ->
                if (success) {
                    coroutineScope.launch {
                        delay(100)
                        listState.animateScrollToItem(tempMessages.size - 1)
                    }
                }
            }

            newMessage = ""
        } else {
            Log.w("MESSAGE_EMPTY", "Intento de enviar mensaje vacío.")
        }
    }

    Scaffold(
        topBar = {
            chatInfo?.let { TopBarChat(navController, it) }
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
                    Text("Chat no disponible", modifier = Modifier.padding(16.dp))
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                state = listState
            ) {
                if (tempMessages.isEmpty()) {
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
                    items(tempMessages) { msg ->
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