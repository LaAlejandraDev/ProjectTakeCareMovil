package com.example.takecare.ui.screens.home.components

import android.widget.Toast
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TopBarMessages(onNewChat: () -> Unit) {
    val context = LocalContext.current

    Surface (
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        contentColor = Color.Black,
        shadowElevation = 1.dp
    ) {
        Row (
            modifier = Modifier.padding(top = 40.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Mensajes", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
            TextButton(
                onClick = {
                    Toast.makeText(context, "Abriendo un nuevo chat", Toast.LENGTH_SHORT).show()
                    onNewChat()
                }
            ) {
                Text("Nuevo Chat")
            }
        }
    }
}