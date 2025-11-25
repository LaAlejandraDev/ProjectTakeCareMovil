package com.example.takecare.ui.screens.profile.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.composeunstyled.Text
import com.example.takecare.data.models.AllData.DiaryAllDataModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

@Composable
fun UserDiaryRegistryScreen(
    diaryList: List<DiaryAllDataModel> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            text = "Número de diarios registrados: ${diaryList.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(12.dp))

        if (diaryList.isEmpty()) {
            Text(
                text = "No hay diarios registrados aún.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(diaryList) { diary ->
                    DiaryItemCard(diary)
                }
            }
        }
    }
}

@Composable
private fun DiaryItemCard(diary: DiaryAllDataModel) {
    Surface (
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Fecha: " + diary.date.slice(0..10).replace("T", "").replace("-", " "),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = diary.emocionalState,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            if (diary.comment.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = diary.comment,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }
    }
}