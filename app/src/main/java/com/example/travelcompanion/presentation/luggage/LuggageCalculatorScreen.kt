package com.example.travelcompanion.presentation.luggage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Locale
import kotlinx.coroutines.launch

enum class FlightType(val displayName: String, val maxWeight: Int) {
    NATIONAL("Nacional", 23),
    INTERNATIONAL("Internacional", 32)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuggageCalculatorScreen(onBack: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var weightInput by remember { mutableStateOf("") }
    var selectedFlightType by remember { mutableStateOf(FlightType.NATIONAL) }
    var resultText by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Calculadora de Equipaje") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = weightInput,
                onValueChange = { weightInput = it },
                label = { Text("Peso de la maleta (kg)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Text("Tipo de vuelo:", style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Start))

            Column(Modifier.selectableGroup()) {
                FlightType.entries.forEach { flightType ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (flightType == selectedFlightType),
                                onClick = { selectedFlightType = flightType },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (flightType == selectedFlightType),
                            onClick = null // null recommended for accessibility with selectable modifier
                        )
                        Text(
                            text = "${flightType.displayName} (Máx: ${flightType.maxWeight} kg)",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            Button(
                onClick = {
                    val weight = weightInput.toDoubleOrNull()
                    if (weight == null) {
                        scope.launch {
                            snackbarHostState.showSnackbar("El peso es obligatorio y debe ser numérico")
                        }
                        resultText = ""
                    } else if (weight <= 0) {
                        scope.launch {
                            snackbarHostState.showSnackbar("El peso debe ser mayor que cero")
                        }
                        resultText = ""
                    } else {
                        val max = selectedFlightType.maxWeight
                        if (weight <= max) {
                            resultText = "Cumple el límite permitido (${selectedFlightType.displayName})"
                        } else {
                            val excess = weight - max
                            resultText = "Excede el límite permitido por ${String.format(Locale.getDefault(), "%.2f", excess)} kg"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular")
            }

            if (resultText.isNotEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = resultText,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
