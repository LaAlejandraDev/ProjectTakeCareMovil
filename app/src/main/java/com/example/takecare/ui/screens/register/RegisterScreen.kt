package com.example.takecare.ui.screens.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.takecare.data.models.PatientModel
import com.example.takecare.data.models.User
import com.example.takecare.ui.navigation.Routes
import com.example.takecare.ui.screens.register.components.ContactDataFrame
import com.example.takecare.ui.screens.register.components.EmergencyDataFrame
import com.example.takecare.ui.screens.register.components.MedicalInfoDataFrame
import com.example.takecare.ui.screens.register.components.PersonalDataFrame

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegisterScreen(
    navController: NavHostController,
    registerViewModel: RegisterViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var firstLastName by remember { mutableStateOf("") }
    var secondLastName by remember { mutableStateOf("") }
    var bornDate by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var diagnostic by remember { mutableStateOf("") }
    var medicalBackground by remember { mutableStateOf("") }
    var martialStatus by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }

    val totalSteps = 4
    var currentStep by remember { mutableStateOf(0) }
    val progress by animateFloatAsState(
        targetValue = (currentStep + 1) / totalSteps.toFloat(),
        animationSpec = tween(600)
    )

    fun createNewUser() {
        val newUser: User = User(
            name = name,
            firstLastName = firstLastName,
            secondLastName = secondLastName,
            email = email,
            phone = phoneNumber,
            password = password,
            type = 0,
        )

        val newPatient: PatientModel = PatientModel(
            city = city,
            martialStatus = martialStatus,
            diagnostic = diagnostic,
            medicalBackground = medicalBackground,
            gender = gender,
            emergencyContact = emergencyContact,
            user = newUser,
            id = 0,
            userId = 0
        )

        registerViewModel.createNewUser(newPatient)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(
                        onClick = {
                            navController.navigate(Routes.Login.route)
                        },
                    ) {
                        Text("Cancelar")
                    }

                    TextButton(
                        onClick = {
                            if (currentStep > 0) currentStep--
                        },
                        enabled = currentStep > 0
                    ) {
                        Text("Anterior")
                    }

                    TextButton(
                        onClick = {
                            if (currentStep < totalSteps - 1) {
                                currentStep++
                            } else {
                                createNewUser()
                            }
                        }
                    ) {
                        Text(if (currentStep < totalSteps - 1) "Siguiente" else "Finalizar")
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AnimatedContent(
                targetState = currentStep,
                transitionSpec = {
                    slideInHorizontally(
                        animationSpec = tween(400),
                        initialOffsetX = { it }
                    ) + fadeIn() togetherWith
                            slideOutHorizontally(
                                animationSpec = tween(400),
                                targetOffsetX = { -it }
                            ) + fadeOut()
                },
                label = "AnimatedFormTransition"
            ) { step ->
                when (step) {
                    0 -> PersonalDataFrame(
                        name,
                        { name = it },
                        firstLastName,
                        { firstLastName = it },
                        secondLastName,
                        { secondLastName = it },
                        bornDate,
                        { bornDate = it },
                        gender,
                        { gender = it }
                    )

                    1 -> ContactDataFrame(
                        email,
                        { email = it },
                        phoneNumber,
                        { phoneNumber = it },
                        password,
                        { password = it }
                    )

                    2 -> MedicalInfoDataFrame(
                        diagnostic,
                        { diagnostic = it },
                        medicalBackground,
                        { medicalBackground = it },
                        martialStatus,
                        { martialStatus = it }
                    )

                    3 -> EmergencyDataFrame(
                        city,
                        { city = it },
                        emergencyContact,
                        { emergencyContact = it }
                    )
                }
            }
        }
    }
}