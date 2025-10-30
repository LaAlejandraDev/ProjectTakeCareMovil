package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.R
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Post

@Composable
fun OpenPost(
    modifier: Modifier = Modifier,
    content : @Composable () -> Unit
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        content()
    }
}

@Composable
fun OpenPostHeader(
    modifier: Modifier = Modifier,
    rootNavController: NavController
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp)
    ) {
        IconButton(
            onClick = {
                rootNavController.navigate("main_scaffold")
            },
            modifier = Modifier
                .size(48.dp)
                .background(Color.White, CircleShape)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                    CircleShape
                )
                .clip(CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Regresar",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun OpenPostBody(openPost: Post?) {
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Creado " + openPost!!.date, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(openPost.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        BadgeComponent(openPost.type.toString(), BadgeType.Neutral)
        Text(
            openPost.content,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify,
            color = Color.Gray
        )
    }
}

@Composable
fun OpenPostActions(openPost: Post?) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Button(
            onClick = { /* TODO: Acción de "Me gusta" */ },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Me Gusta",
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = openPost?.likesCount.toString(),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
            )
        }

        BadgeIconComponent (
            type = BadgeType.Neutral,
            horizontalPadding = 16.dp,
            verticalPadding = 10.dp
        ){
            Row (horizontalArrangement = Arrangement.spacedBy(8.dp)){
                Icon(
                    painterResource(R.drawable.message),
                    contentDescription = "Comentarios",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = openPost?.commentCount.toString(),
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium)
                )
            }
        }

    }
}

@Composable
fun OpenPostAvatarHeader(openPost: Post?) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp
    ) {
        Row (
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Avatar(initials = openPost!!.user.getInitials())
                Text(openPost.user.name, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            BadgeComponent(openPost!!.user.type.toString(), BadgeType.Primary)
        }
    }
}

@Composable
fun OpenPostComments(openPost: Post?, modifier: Modifier = Modifier) {
    val comments = emptyList<Comment>()

    Surface(
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Comentarios",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (comments.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Aún no hay comentarios",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    comments.forEach { item ->
                        CommentComponent(item, onClick = {})
                    }
                }
            }
        }
    }
}