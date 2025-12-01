package com.example.takecare.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.takecare.ui.navigation.HomeRoutes

@Composable
fun FloatingPostButton(
    navController: NavController,
    buttonSize: ButtonSize = ButtonSize.SMALL,
    content: @Composable () -> Unit = {}
) {

    FloatingActionButton(
        onClick = { navController.navigate(HomeRoutes.CreatePost.route) },
        shape = RoundedCornerShape(16.dp),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        ),
        modifier = Modifier.size(buttonSize.size)
    ) {
        content()
    }
}

enum class ButtonSize(val size: Dp) {
    SMALL(40.dp),
    MEDIUM(56.dp),
    LARGE(72.dp)
}
