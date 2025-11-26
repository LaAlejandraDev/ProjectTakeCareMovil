package com.example.takecare.ui.screens.dairy

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.takecare.data.models.Insert.DiaryInsertModel
import com.example.takecare.ui.navigation.HomeRoutes
import com.example.takecare.ui.screens.dairy.sections.DiaryConfirmationSection
import com.example.takecare.ui.screens.dairy.sections.DiaryWriteSection
import com.example.takecare.ui.screens.dairy.sections.IntroductionSection
import com.example.takecare.ui.screens.psycologist.AlertDialogError
import androidx.compose.runtime.collectAsState
import com.example.takecare.ui.components.DialogSimple

@Composable
fun DiaryScreen(
    navController: NavController,
    viewModel: DiaryViewModel = viewModel()
) {
    val context = LocalContext.current
    var sectionIndex by remember { mutableStateOf(0) }
    val openAlertDialog = remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    val dialogTitle = remember { mutableStateOf("Gracias por compartir tu día") }
    val patient = viewModel.selectedPatient.collectAsState().value
    val dialogContent = remember {
        mutableStateOf(
            "Tu diario de hoy está completo.\n" +
                    "Mañana tendrás un nuevo espacio para seguir expresándote."
        )
    }
    fun onNextSection() {
        if (sectionIndex > -1 && sectionIndex < 3) {
            sectionIndex++
        }
    }

    fun onCreateNewDiary() {
        if (text.length < 10 || text.isEmpty()) {
            return
        }

        if (!text.isEmpty()) {
            onNextSection()
            val newDiary = DiaryInsertModel(
                patient?.id!!,
                0,
                "",
                text
            )
            viewModel.createNewDiary(newDiary)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getPatient(context)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            openAlertDialog.value -> {
                AlertDialogError(
                    onConfirmation = {
                        openAlertDialog.value = false
                        navController.navigate(HomeRoutes.Home.route)
                    },
                    onDismissRequest = {
                        openAlertDialog.value = false
                        navController.navigate(HomeRoutes.Home.route)
                    },
                    dialogTitle = dialogTitle.value,
                    dialogText = dialogContent.value
                )
            }

            sectionIndex == 0 -> {
                IntroductionSection({ onNextSection() })
            }

            sectionIndex == 1 -> {
                DiaryWriteSection(text = text, onValueChange = { text = it }, { onCreateNewDiary() })
            }

            sectionIndex == 2 -> {
                DiaryConfirmationSection(
                    diaryState = viewModel.diaryState.collectAsState().value,
                    {  })
            }

            else -> {
                Text("Ocurrio un error")
            }
        }
    }
}