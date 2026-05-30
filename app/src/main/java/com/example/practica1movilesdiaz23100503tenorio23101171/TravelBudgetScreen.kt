package com.example.practica1movilesdiaz23100503tenorio23101171

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun TravelBudgetScreen(onBack: () -> Unit) {
    var days by remember { mutableStateOf("") }
    var dailyBudget by remember { mutableStateOf("") }
    var accommodationType by remember { mutableStateOf("Estándar") }
    var expanded by remember { mutableStateOf(false) }
    var totalBudget by remember { mutableStateOf<Double?>(null) }
    var resultMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val accommodationOptions = mapOf(
        "Económico" to 0.8,
        "Estándar" to 1.0,
        "Premium" to 1.5
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Cálculo de Presupuesto de Viaje",
                style = MaterialTheme.typography.headlineSmall
            )

            OutlinedTextField(
                value = days,
                onValueChange = { days = it },
                label = { Text("Cantidad de días") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = dailyBudget,
                onValueChange = { dailyBudget = it },
                label = { Text("Presupuesto diario") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Alojamiento: $accommodationType")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    accommodationOptions.keys.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                accommodationType = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val d = days.toIntOrNull()
                    val b = dailyBudget.toDoubleOrNull()
                    val factor = accommodationOptions[accommodationType] ?: 1.0

                    if (days.isBlank() || dailyBudget.isBlank()) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Todos los campos son obligatorios")
                        }
                    } else if (d == null || d <= 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("Los días deben ser mayores que cero")
                        }
                    } else if (b == null || b <= 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("El presupuesto diario debe ser mayor que cero")
                        }
                    } else {
                        val total = d * b * factor
                        totalBudget = total
                        resultMessage = "Escenario: Viaje de $d días con alojamiento $accommodationType."
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            totalBudget?.let {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = resultMessage, style = MaterialTheme.typography.bodyLarge)
                        Text(
                            text = "Presupuesto Total: ${String.format(Locale.US, "$%.2f", it)}",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Volver al menú principal")
            }
        }
    }
}
