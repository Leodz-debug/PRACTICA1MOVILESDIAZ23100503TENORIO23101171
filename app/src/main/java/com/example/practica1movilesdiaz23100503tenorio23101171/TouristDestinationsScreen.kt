package com.example.practica1movilesdiaz23100503tenorio23101171

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.util.Locale

@Composable
fun TouristDestinationsScreen(onBack: () -> Unit) {
    val destinations = listOf(
        Destination("Perú", "Cusco", 150.0, "https://images.unsplash.com/photo-1587595431973-160d0d94add1"),
        Destination("Francia", "París", 300.0, "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
        Destination("Japón", "Tokio", 250.0, "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
        Destination("Italia", "Roma", 200.0, "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
        Destination("EE.UU.", "Nueva York", 350.0, "https://images.unsplash.com/photo-1496442226666-8d4d0e62e6e9")
    )

    val totalCost = destinations.sumOf { it.costoPromedio }

    Scaffold(
        bottomBar = {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Volver al menú principal")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Destinos Turísticos",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(destinations) { destination ->
                    DestinationCard(destination)
                }

                item {
                    SummaryCard(count = destinations.size, totalCost = totalCost)
                }
            }
        }
    }
}

@Composable
fun DestinationCard(destination: Destination) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = destination.imageUrl,
                contentDescription = destination.ciudad,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = destination.ciudad,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = destination.pais,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = "Costo promedio: ${String.format(Locale.US, "$%.2f", destination.costoPromedio)}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun SummaryCard(count: Int, totalCost: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Resumen del Listado",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cantidad total de destinos: $count")
            Text(
                text = "Suma acumulada de costos: ${String.format(Locale.US, "$%.2f", totalCost)}",
                fontWeight = FontWeight.Bold
            )
        }
    }
}
