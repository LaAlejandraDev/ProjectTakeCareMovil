package com.example.takecare.ui.screens.messages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.data.models.MessageTemp
import com.example.takecare.ui.screens.messages.components.MessageComponent
import com.microsoft.signalr.HubConnection
import com.microsoft.signalr.HubConnectionBuilder

@Composable
fun MessagesScreen(navController: NavController) {
    Column (
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        Text("Seccion de chats abiertos")
        Button(
            onClick = { navController.navigate("chat_scaffold") }
        ) { Text("Abrir chat")}
    }
}