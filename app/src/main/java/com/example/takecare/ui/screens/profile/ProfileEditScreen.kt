package com.example.takecare.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.takecare.ui.screens.forum.components.Avatar
import com.example.takecare.ui.screens.profile.components.TextFieldUser

@Preview(showBackground = true)
@Composable
fun ProfileScreen() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Avatar("J", size = 60.dp)
        Text("Juan Pablo", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
        TextFieldUser(
            value = "Juan Pablo",
            onValueChange = {},
            placeholder = "Nombre"
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            TextFieldUser(
                modifier = Modifier.weight(1f),
                value = "Perez",
                onValueChange = {},
                placeholder = "Apellido Paterno"
            )
            TextFieldUser(
                modifier = Modifier.weight(1f),
                value = "Fernandez",
                onValueChange = {},
                placeholder = "Apellido Materno"
            )
        }
    }
}