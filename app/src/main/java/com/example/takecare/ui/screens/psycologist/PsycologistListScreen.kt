package com.example.takecare.ui.screens.psycologist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.takecare.ui.screens.psycologist.components.TopBarPsyco

@Composable
fun PsycoListScreen() {
    Scaffold (
        topBar = {
            TopBarPsyco()
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier.padding(innerPadding)
        ) {
            item {
                PsycoListItem("Especialidad", 4.3f)
            }
        }
    }
}

@Composable
fun PsycoListItem(
    speciality: String,
    grade: Float
) {
    Row (
        modifier = Modifier.fillMaxWidth().padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(speciality)
        Text(grade.toString())
    }
}