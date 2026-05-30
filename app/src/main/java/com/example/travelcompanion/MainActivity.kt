package com.example.travelcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.travelcompanion.presentation.navigation.AppNavGraph
import com.example.travelcompanion.ui.theme.TravelCompanionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelCompanionTheme {
                AppNavGraph()
            }
        }
    }
}
// integrantes:
// Leonardo Diaz 23100503
// Santiago tenorio 23101171