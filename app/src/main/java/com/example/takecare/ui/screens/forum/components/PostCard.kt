package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post
import com.example.takecare.ui.screens.forum.ForumViewModel

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    postData: Post?,
    isExpanded: Boolean = false,
    onLikeClick: () -> Unit = {},
    onViewClick: () -> Unit = {}
) {
    if (postData == null) {
        Text("Post no disponible", modifier = modifier.padding(16.dp))
        return
    }

    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardHeader(postData)
            CardBody(postData, isExpanded)
            if (!isExpanded) {
                CardActions(onLikeClick, onViewClick)
            } else {
                CardCommentBox()
                CardCommentSection(postData)
            }
        }
    }
}

@Composable
fun CardHeader(postData: Post) {
    val user = postData.user

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Avatar(user?.getInitials() ?: "")
            Column {
                Text(
                    user?.name ?: "Usuario desconocido",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    postData.date?.take(10) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            BadgeComponent(postData.getPostTypeText() ?: "Sin tipo", BadgeType.Success)
            BadgeComponent(user?.getUserType() ?: "Desconocido", BadgeType.Primary)
        }
    }
}

@Composable
fun CardBody(postData: Post, isExpanded: Boolean = false) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            postData.title ?: "",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = postData.content ?: "",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun CardCommentBox(
    modifier: Modifier = Modifier,
) {
    var response by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = response,
            onValueChange = { response = it },
            placeholder = { Text("Deja una respuesta...") },
            singleLine = true,
            shape = CircleShape,
            modifier = Modifier
                .weight(1f)
                .height(54.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        )

        Button(
            onClick = {
                //createNewComment()
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .size(54.dp),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(Icons.Default.Check, contentDescription = "Enviar")
        }
    }
}


@Composable
fun CardCommentSection(postData: Post, forumViewModel: ForumViewModel = viewModel()) {
    val commentList = forumViewModel.postCommentList.collectAsState().value.orEmpty()

    Column(modifier = Modifier.fillMaxWidth()) {
        if (commentList.isEmpty()) {
            Text(
                "Aún no hay comentarios",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            commentList.filterNotNull().forEach { comment ->
                CommentComponent(comment, modifier = Modifier.fillMaxWidth(), onClick = {})
            }
        }
    }
}

@Composable
fun CardActions(
    onLikeClick: () -> Unit,
    onViewClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextButton(onClick = onLikeClick) {
            Icon(Icons.Default.Favorite, contentDescription = "Like", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(4.dp))
            Text("Like")
        }
        Button(onClick = onViewClick) {
            Text("Ver más")
        }
    }
}