package com.example.simpleserverapp.sampledata

import android.net.nsd.NsdServiceInfo
import java.net.InetAddress
import java.net.UnknownHostException

fun mockNsdServices(): List<NsdServiceInfo> {
    val mockMap = mapOf(
        "TestServerOne" to "192.168.1.10",
        "CameraStreamLocal" to "192.168.1.25",
        "MediaShareBox" to "192.168.1.33",
        "SimpleServer" to "192.124.5.12",
        "TestServerOne1" to "192.168.1.10",
        "CameraStreamLocal1" to "192.168.1.25",
        "MediaShareBox1" to "192.168.1.33",
        "SimpleServer1" to "192.124.5.12",
        "TestServerOne2" to "192.168.1.10",
        "CameraStreamLocal2" to "192.168.1.25",
        "MediaShareBox2" to "192.168.1.33",
        "SimpleServer2" to "192.124.5.12",
        "TestServerOne3" to "192.168.1.10",
        "CameraStreamLocal3" to "192.168.1.25",
        "MediaShareBox3" to "192.168.1.33",
        "SimpleServer3" to "192.124.5.12",
    )

    return mockMap.mapNotNull { (name, ip) ->
        try {
            if (name == "TestServerOne") {
                NsdServiceInfo().apply {
                    serviceName = name
                    serviceType = "_http._tcp."
                    port = 8000
                    hostAddresses = mutableListOf(InetAddress.getByName(ip), InetAddress.getByName(ip + "2"))
                }
            } else {
                NsdServiceInfo().apply {
                    serviceName = name
                    serviceType = "_http._tcp."
                    port = 8000
                    hostAddresses = mutableListOf(InetAddress.getByName(ip))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}