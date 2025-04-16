package com.example.simpleserverapp

import android.net.nsd.NsdServiceInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateListOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.simpleserverapp.core.model.ServerInfo
import com.example.simpleserverapp.core.network.NetworkDiscover
import com.example.simpleserverapp.views.ui.theme.SimpleServerAppTheme
import com.example.simpleserverapp.views.filetransfer.FileTransferScreen
import com.example.simpleserverapp.views.start.ServerDiscoveryScreen
import com.example.simpleserverapp.views.navigation.Screen

class MainActivity : ComponentActivity() {

    private lateinit var nsdHelper: NetworkDiscover


    @RequiresExtension(extension = Build.VERSION_CODES.TIRAMISU, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nsdHelper = NetworkDiscover(this)
        setContent {
            val navController = rememberNavController()
            val services = remember { mutableStateListOf<NsdServiceInfo>() }

            LaunchedEffect(Unit) {
                nsdHelper.startDiscovery {
                    services.clear()
                    services.addAll(it)
                }
            }

            NavHost(
                navController = navController, startDestination = Screen.ServerDiscovery.route
            ) {
                composable(Screen.ServerDiscovery.route) {
                    SimpleServerAppTheme {
                        ServerDiscoveryScreen(
                            services =  services,
                            onRefresh = {
                                nsdHelper.startDiscovery {
                                    services.clear()
                                    services.addAll(it)
                                }
                            },
                            onServerSelected = {server ->
                                navController.navigate(Screen.FileTransfer.createRoute(server))
                            }
                        )
                    }
                }

                composable(
                    route = Screen.FileTransfer.route,
                    arguments = listOf(
                        navArgument("serverName") { type = NavType.StringType},
                        navArgument("ip") { type = NavType.StringType },
                    )
                ) { backStackEntry ->
                    val serverName = backStackEntry.arguments?.getString("serverName") ?: ""
                    val ip = backStackEntry.arguments?.getString("ip") ?: ""

                    SimpleServerAppTheme {
                        FileTransferScreen(ServerInfo(name = serverName, ip = ip))
                    }
                }
            }

        }
    }
}