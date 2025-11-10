package com.example.takecare.ui.screens.messages.components

import ChatAllDataModel
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.data.models.Insert.ChatModel
import com.example.takecare.ui.navigation.HomeRoutes

@Composable
fun TopBarChat(navController: NavController, chatData: ChatAllDataModel) {
    val context = LocalContext.current

    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surfaceVariant,
        tonalElevation = 4.dp,
        contentColor = Color.Black
    ) {
        Row (
            modifier = Modifier.fillMaxWidth().padding(top = 30.dp, end = 8.dp, start = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = {
                    Toast.makeText(context, "Regresando", Toast.LENGTH_SHORT).show()
                    navController.navigate("main_scaffold")
                }
            ) {
                Text("Regresar", color = Color.Black)
            }
            Text("${chatData.psychologistName}")
        }
    }
}