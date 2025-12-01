package com.example.takecare.ui.screens.forum.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.android.volley.toolbox.ImageRequest
import com.example.takecare.ui.components.DialogSimple

@Composable
fun Avatar(
    initials: String,
    imageUrl: String = "",
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = Color.White,
) {
    val shape = CircleShape

    if (imageUrl.isBlank()) {
        Box(
            modifier = modifier
                .size(size)
                .background(backgroundColor, shape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials.uppercase(),
                color = contentColor,
                fontSize = (size.value / 2).sp,
                fontWeight = FontWeight.Medium
            )
        }

    } else {
        AsyncImage(
            model = imageUrl,
            modifier = modifier.size(size).clip(CircleShape).background(Color.White),
            contentScale = ContentScale.Crop,
            contentDescription = "Avatar",
        )
    }
}