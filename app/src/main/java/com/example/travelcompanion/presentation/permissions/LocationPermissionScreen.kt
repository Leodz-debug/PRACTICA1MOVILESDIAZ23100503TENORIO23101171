package com.example.travelcompanion.presentation.permissions

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

enum class PermissionStatus {
    PENDING,
    GRANTED,
    DENIED
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationPermissionScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    
    // Función para obtener el estado actual del permiso
    fun checkPermission(): PermissionStatus {
        val hasFine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val hasCoarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        return if (hasFine || hasCoarse) PermissionStatus.GRANTED else PermissionStatus.PENDING
    }

    var status by remember { mutableStateOf(checkPermission()) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                        permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
        status = if (isGranted) PermissionStatus.GRANTED else PermissionStatus.DENIED
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Permiso de Ubicación") },
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
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val statusMessage = when (status) {
                PermissionStatus.PENDING -> "Permiso pendiente de solicitud"
                PermissionStatus.GRANTED -> "Permiso concedido"
                PermissionStatus.DENIED -> "Permiso denegado"
            }

            Text(
                text = "Estado del permiso:",
                style = MaterialTheme.typography.titleMedium
            )
            
            Text(
                text = statusMessage,
                style = MaterialTheme.typography.headlineSmall,
                color = if (status == PermissionStatus.GRANTED) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            if (status != PermissionStatus.GRANTED) {
                Button(
                    onClick = {
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (status == PermissionStatus.DENIED) "Reintentar Solicitud" else "Conceder Permiso")
                }
            } else {
                Text(
                    text = "Asistencia de viaje activada con tu ubicación.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
