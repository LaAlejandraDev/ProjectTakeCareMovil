package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.ui.text.style.TextOverflow
import com.example.takecare.data.exampleData.sampleComments
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post
import com.example.takecare.data.models.User

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    postData: Post,
    onLikeClick: () -> Unit = {},
    onViewClick: () -> Unit = {}
) {
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
            CardBody(postData)
            CardActions(onLikeClick, onViewClick)
        }
    }
}

@Composable
fun CardHeader(postData: Post) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Avatar(postData.user.getInitials())
            Column {
                Text(
                    postData.user.userName,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    postData.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            BadgeComponent(postData.type.displayName, BadgeType.Success)
            BadgeComponent(postData.user.type.displayName, BadgeType.Primary)
        }
    }
}

@Composable
fun CardBody(postData: Post) {
    val commentsForPost = sampleComments.filter { it.postId == postData.id }
    val topComment = commentsForPost.maxByOrNull { it.likes }

    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            postData.title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = postData.content,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )

        topComment?.let {
            Comment(it, isTop = true)
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