package com.ali.smartlight.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun MainScreen(viewModel: SmartLightViewModel) {
    val isConnected by viewModel.isConnected.collectAsState()
    val lightState by viewModel.lightState.collectAsState()
    val brightness by viewModel.brightness.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.connect()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp,
                    vertical = 48.dp
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Smart Light App",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 20.dp)
            )

            Text(
                text = if (isConnected) "Connected to MQTT" else "Connecting...",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Light: ")
                Switch(
                    checked = lightState,
                    onCheckedChange = {
                        viewModel.toggleLight()
                    },
                    enabled = true
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Brightness: $brightness")
                Slider(
                    value = brightness.toFloat(),
                    onValueChange = { viewModel.setBrightness(it.toInt()) },
                    valueRange = 0f..100f,
                    enabled = true
                )
            }
        }
    }
}