package com.example.travelcompanion.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.travelcompanion.data.model.Destination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(onNavigate: (Destination) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Travel Companion App") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onNavigate(Destination.LuggageCalculator) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Calculadora de Equipaje")
            }
            Button(
                onClick = { onNavigate(Destination.TravelBudget) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Planificador de Presupuesto de Viaje")
            }
            Button(
                onClick = { onNavigate(Destination.TouristDestinations) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Catálogo de Destinos Turísticos")
            }
            Button(
                onClick = { onNavigate(Destination.LocationPermission) },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
            ) {
                Text("Permiso de Ubicación para Asistencia de Viaje")
            }
        }
    }
}
