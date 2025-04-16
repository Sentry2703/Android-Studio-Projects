package com.example.simpleserverapp.views.start

import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simpleserverapp.core.model.ServerInfo
import com.example.simpleserverapp.data.sample.mockNsdServices

@RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
@Composable
fun ServerDiscoveryScreen (
    services: List<NsdServiceInfo>,
    onRefresh: () -> Unit,
    onServerSelected: (ServerInfo) -> Unit
){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Discovered Servers",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn (
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (services.isEmpty()) {
                    item {
                        Text("No servers found.", style = MaterialTheme.typography.bodyMedium)
                    }
                } else {
                    items(services) { service ->
                        val ip = service.hostAddresses[0]?.hostAddress ?: ""
                        ServerCard(service) {
                            onServerSelected(ServerInfo(service.serviceName, ip))
                        }
                    }
                }
            }
        }

        Row (
            modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 12.dp)
        ) {
            FloatingActionButton(
                onClick = onRefresh,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Refresh")
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
@Composable
fun ServerCard(service: NsdServiceInfo, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column (modifier = Modifier.padding(16.dp)) {
            Text(
                text = service.serviceName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            service.hostAddresses.forEach { address ->
                Text(
                    text = "IP: $address",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
@Composable
@Preview(showBackground = true)
fun ServerScreen() {
    ServerDiscoveryScreen(
        services = mockNsdServices(), //services
        onRefresh = {
        },
        onServerSelected = { a ->
            Log.d("ServerNSD", a.name + a.ip)
        }
    )
}
