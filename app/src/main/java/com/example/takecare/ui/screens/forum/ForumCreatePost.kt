package com.example.takecare.ui.screens.forum

import android.util.Log
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.takecare.data.models.Comment
import com.example.takecare.data.models.Insert.PostModelCreate
import com.example.takecare.data.models.Post
import com.example.takecare.data.models.PostType
import com.example.takecare.ui.components.DialogComponent
import com.example.takecare.ui.components.DialogSimple
import com.example.takecare.ui.screens.profile.ProfileViewModel
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForumCreatePost(forumViewModel: ForumViewModel = viewModel(), profileViewModel: ProfileViewModel = viewModel()) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(PostType.QUESTION) }
    val localUser by profileViewModel.selectedUser.collectAsState()
    val context = LocalContext.current
    val isoDate = Instant.now().toString()
    var showDialog by remember { mutableStateOf(false) }
    var isPostCreated by remember { mutableStateOf(false) }
    var validationError by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        profileViewModel.selectUser(context)
    }

    fun createNewPost() {
        val user = localUser
        if (user != null && title.length >= 25 && content.length >= 50) {
            val newPost = PostModelCreate(
                title = title,
                content = content,
                date = isoDate,
                type = selectedType.ordinal,
                userId = user.id!!,
                isAnonymous = false
            )
            forumViewModel.addPost(newPost) { isSuccess ->
                showDialog = true
                if (isSuccess) {
                    isPostCreated = true
                } else {
                    isPostCreated = false
                }
            }
        } else {
            validationError = true
        }
    }

    Column(
        modifier = Modifier
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (localUser != null) {
            Text(
                text = "Crear nuevo post",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = "Comparte tus pensamientos, preguntas o experiencias con la comunidad. " +
                        "Recuerda mantener un tono respetuoso y constructivo.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )
            )

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título del post") },
                placeholder = { Text("Ej. Mi experiencia con la recolección de agua") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Título"
                    )
                }
            )

            Text("Tipo de post:", style = MaterialTheme.typography.bodyMedium)
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                PostType.entries.forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { selectedType = type },
                        label = { Text(type.displayName) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Contenido") },
                placeholder = { Text("Escribe aquí el contenido de tu publicación...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 200.dp),
                maxLines = 10
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                onClick = {
                    createNewPost()
                }
            ) {
                Text("Publicar", color = Color.White)
            }

            if (showDialog) {
                DialogSimple(
                    title = if (isPostCreated) "Publicación creada" else "No se pudo crear la publicación",
                    text = if (isPostCreated) "Tu publicación se ha creado correctamente y ya está disponible."
                    else "Ocurrió un problema al crear tu publicación. Por favor, inténtalo de nuevo más tarde.",
                    icon = if (isPostCreated) Icons.Default.Check else Icons.Default.Clear,
                    onConfirm = { showDialog = false },
                    onDismiss = { showDialog = false }
                )
            }

            if (validationError) {
                DialogSimple(
                    title = "Error",
                    text = "El contenido de la publicacion no cumple con los requisitos",
                    icon = Icons.Default.Warning,
                    onDismiss = {
                        validationError = false
                    },
                    onConfirm = {
                        validationError = false
                    }
                )
            }
        } else {
            Text(
                text = "No se pudo cargar el usuario. Inicia sesión nuevamente.",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}