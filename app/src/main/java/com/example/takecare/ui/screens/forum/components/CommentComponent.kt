package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.takecare.data.models.Comment
import com.example.takecare.ui.screens.forum.ForumViewModel

@Composable
fun CommentComponent(
    comment: Comment,
    isTop: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                onClick()
            }
            .background(
                if (isTop) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column (
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Avatar(comment.user!!.getInitials(), size = 24.dp)
            Text(comment.likes.toString(), style = MaterialTheme.typography.bodySmall)
        }
        Column {
            comment.user?.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            comment.content?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (isTop) MaterialTheme.colorScheme.primary else Color.Gray
                )
            }
        }
    }
}
