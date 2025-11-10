package com.example.takecare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.rememberDialogState
import com.composeunstyled.Button
import com.composeunstyled.Text

@Composable
fun DialogComponent(
    title: String,
    message: String,
    confirmText: String = "Aceptar",
    showButton: Boolean = true,
    onConfirm: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    triggerContent: @Composable (() -> Unit)? = null
) {
    val dialogState = rememberDialogState()

    dialogState.visible = true

    Box {
        Dialog(
            state = dialogState
        ) {
            DialogPanel(
                modifier = Modifier
                    .displayCutoutPadding()
                    .systemBarsPadding()
                    .widthIn(min = 280.dp, max = 560.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE4E4E4), RoundedCornerShape(12.dp))
                    .background(Color.White)
            ) {
                Column {
                    Column(Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)) {
                        Text(
                            text = title,
                            style = TextStyle(fontWeight = FontWeight.Medium)
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = message,
                            style = TextStyle(color = Color(0xFF474747))
                        )
                    }
                    if (showButton) {
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = {
                                dialogState.visible = false
                                onConfirm?.invoke()
                            },
                            modifier = Modifier
                                .padding(12.dp)
                                .align(Alignment.End),
                            shape = RoundedCornerShape(4.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = confirmText,
                                color = Color(0xFF6699FF)
                            )
                        }
                    }
                }
            }
        }
    }
}