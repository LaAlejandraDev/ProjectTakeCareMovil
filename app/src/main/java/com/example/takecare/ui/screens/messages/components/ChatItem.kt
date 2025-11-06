import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import com.example.takecare.ui.screens.forum.components.Avatar
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ChatItem(
    onClickChat: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(20.dp).clickable{ onClickChat() },
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(initials = "J")
        Column {
            ChatHeader("Juan Pablo")
            ChatBody("Hola papu, como estas?")
        }
    }
}

@Composable
fun ChatHeader(
    userName: String,
) {
    Text(userName, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
}

@Composable
fun ChatBody(
    content: String
) {
    Text(content, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
}