package com.example.simpleserverapp.NetworkHelper

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log

class NetworkDiscover (context: Context){
    private val nsdManager: NsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    private var discoveryListener: NsdManager.DiscoveryListener ? = null

    private val serviceType = "_http._tcp." //Service Type for HTTP
    val discoveredServices = mutableListOf<NsdServiceInfo>()

    fun startDiscovery(onUpdate: (List<NsdServiceInfo>) -> Unit) {
        stopDiscovery()
        discoveredServices.clear()

        discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onStartDiscoveryFailed(p0: String?, p1: Int) {
                Log.e("NSD", "Discovery start failed: Error code: $p1")
            }

            override fun onStopDiscoveryFailed(p0: String?, p1: Int) {
                Log.e("NSD", "Discovery stop failed: Error code: $p1")
            }

            override fun onDiscoveryStarted(p0: String?) {
                Log.d("NSD", "Service discovery started")
            }

            override fun onDiscoveryStopped(p0: String?) {
                Log.d("NSD", "Service discovery stopped")
            }

            override fun onServiceFound(service: NsdServiceInfo) {
                if (discoveredServices.contains(service)) {
                    return;
                }
                Log.d("NSD", "Service found: $service")

                nsdManager.resolveService(service, object: NsdManager.ResolveListener {
                    override fun onResolveFailed(p0: NsdServiceInfo?, p1: Int) {
                        Log.e("NSD", "Resolve failed: Error code: $p1")
                    }

                    override fun onServiceResolved(resolvedService: NsdServiceInfo) {
                        Log.d("NSD", "Service resolved: $resolvedService")
                        discoveredServices.add(resolvedService)
                        onUpdate(discoveredServices.toList())
                    }
                })
            }

            override fun onServiceLost(p0: NsdServiceInfo) {
                Log.d("NSD", "Service lost: $p0")
                discoveredServices.remove(p0)
                onUpdate(discoveredServices.toList())
            }
        }

        nsdManager.discoverServices(
            serviceType,
            NsdManager.PROTOCOL_DNS_SD,
            discoveryListener
        )
    }

    private fun stopDiscovery() {
        discoveryListener ?.let {
            nsdManager.stopServiceDiscovery(it)
        }
    }

}