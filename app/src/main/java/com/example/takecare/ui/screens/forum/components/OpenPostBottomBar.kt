package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.takecare.R

@Composable
fun OpenPostBottomBar(
    onSendClick: () -> Unit,
    onContentChange: (String) -> Unit,
    commentText: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.background,
        tonalElevation = 3.dp,
        shadowElevation = 6.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(22.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = commentText,
                onValueChange = onContentChange,
                placeholder = { Text("Escribe algo...") },
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .clip(RoundedCornerShape(50))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outline.copy(alpha = 0f),
                        RoundedCornerShape(50)
                    ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true,
                shape = RoundedCornerShape(50)
            )

            IconButton(
                onClick = {
                    if (commentText.isNotBlank()) {
                        onSendClick()
                    }
                },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    painterResource(R.drawable.send),
                    contentDescription = "Enviar comentario",
                    tint = Color.Black,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
