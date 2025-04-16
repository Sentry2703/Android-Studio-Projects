package com.example.simpleserverapp.views.navigation

import com.example.simpleserverapp.core.model.ServerInfo

sealed class Screen (val route: String) {
    object ServerDiscovery : Screen("server")
    object FileTransfer : Screen("file_transfer/{serverName}/{ip}") {
        fun createRoute(server: ServerInfo) = "file_transfer/${server.name}/${server.ip}"
    }
}