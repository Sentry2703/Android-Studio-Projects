package com.example.simpleserverapp

import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.simpleserverapp.NetworkHelper.NetworkDiscover
import com.example.simpleserverapp.ui.theme.SimpleServerAppTheme

class MainActivity : ComponentActivity() {

    private lateinit var nsdHelper: NetworkDiscover

    @RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nsdHelper = NetworkDiscover(this)
        nsdHelper.startDiscovery()
        setContent {
            SimpleServerAppTheme {
                Column {
                    Text(text = "ServerList")
                    Column {
                        nsdHelper.discoveredServices.forEach { service ->
                            Text(text = "${service.serviceName} Address: ${service.hostAddresses}")
                        }
                    }
                    Text(text = "End of ServerList")
                }
            }
        }
    }
}


