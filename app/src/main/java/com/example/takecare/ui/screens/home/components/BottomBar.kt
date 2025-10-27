import androidx.compose.foundation.border
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.takecare.ui.navigation.HomeRoutes

@Composable
fun BottomTabBar(navController: NavController, currentRoute: String?) {
    val items = listOf(HomeRoutes.Home, HomeRoutes.Messages, HomeRoutes.Diary, HomeRoutes.Profile)

    val selectedIndex = items.indexOfFirst { it.route == currentRoute }
        .takeIf { it >= 0 } ?: 0

    TabRow(
        selectedTabIndex = selectedIndex,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = Color.Black,
        modifier = Modifier
            .border(
                width = 0.5.dp,
                color = Color.Gray.copy(alpha = 0.5f)
            ),
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                text = { Text(item.label, fontWeight = FontWeight.Thin) }
            )
        }
    }
}