package com.example.takecare.ui.screens.messages.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun MessageComponent(
    message: String,
    owner: Boolean = false
) {
    val bubbleColor = if (owner)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surfaceVariant

    val textColor = if (owner)
        MaterialTheme.colorScheme.onPrimaryContainer
    else
        MaterialTheme.colorScheme.onSurfaceVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = if (owner) Arrangement.End else Arrangement.Start
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 280.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (owner) 16.dp else 0.dp,
                        bottomEnd = if (owner) 0.dp else 16.dp
                    )
                )
                .background(bubbleColor)
                .padding(12.dp),
            horizontalAlignment = if (owner) Alignment.End else Alignment.Start
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(color = textColor)
            )
        }
    }
}
