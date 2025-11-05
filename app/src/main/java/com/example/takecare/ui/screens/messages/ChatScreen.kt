package com.example.takecare.ui.screens.messages

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.data.models.MessageTemp
import com.example.takecare.ui.screens.forum.components.OpenPostBottomBar
import com.example.takecare.ui.screens.messages.components.MessageComponent
import com.example.takecare.ui.screens.messages.components.TopBarChat
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(navController: NavController) {
    var newMessage by remember { mutableStateOf("") }
    val messageList = remember { mutableStateListOf<MessageTemp>() }

    val hubConnection: HubConnection = remember {
        HubConnectionBuilder.create("http://192.168.0.107:5002/chathub").build()
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val currentUser = "AppAndroid"

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
            hubConnection.send("SendMessage", currentUser, newMessage)
            newMessage = ""
        }
    }

    Scaffold(
        topBar = {
            TopBarChat(navController)
        },
        bottomBar = {
            OpenPostBottomBar(
                onSendClick = { sendMessage() },
                onContentChange = { newMessage = it },
                commentText = newMessage
            )
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