package com.example.takecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.takecare.ui.navigation.AppNavGraph
import com.example.takecare.ui.theme.TakeCareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeCareTheme {
                val navController = rememberNavController()
                Surface (
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(navController)
                }
            }
        }
    }
}