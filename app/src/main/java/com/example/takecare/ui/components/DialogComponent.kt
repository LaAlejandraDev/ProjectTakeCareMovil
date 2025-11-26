package com.example.takecare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composeunstyled.Icon
import com.composeunstyled.Text
import com.example.takecare.data.models.Comment
import com.example.takecare.ui.screens.forum.components.Avatar
import com.example.takecare.ui.theme.TakeCareTheme

@Composable
fun DialogSimple(
    title: String,
    text: String,
    icon: ImageVector? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 4.dp,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .sizeIn(minHeight = 200.dp, maxHeight = 220.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Spacer(modifier = Modifier.padding(0.dp, 4.dp))
                    }
                    Text(
                        title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.padding(0.dp, 4.dp))
                    Text(
                        text,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Surface(
                    modifier = Modifier
                        .weight(.4f)
                        .fillMaxWidth(),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 0.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            onClick = { onConfirm() }
                        ) {
                            Text("Entendido", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DialogCommentExpanded(
    comment: Comment,
    icon: ImageVector? = Icons.Default.Clear,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {

        Surface(
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFF1A1028),
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {

            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF2C1F3B),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(4.dp)
                ) {

                    Avatar(
                        initials = comment.user?.name?.take(1) ?: "?",
                        size = 48.dp
                    )

                    Spacer(Modifier.width(12.dp))

                    Column {
                        Text(
                            text = comment.user?.name ?: "Unknown User",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Text(
                            text = "@${comment.user?.name ?: "unknown"}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFFB8A9C9)
                            )
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 140.dp, max = 300.dp)
                        .background(
                            color = Color(0xFF2C1F3B),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = comment.content ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFFE7DAF7)
                        )
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Cerrar",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onConfirm() }
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color(0xFFE7DAF7),
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
    }
}