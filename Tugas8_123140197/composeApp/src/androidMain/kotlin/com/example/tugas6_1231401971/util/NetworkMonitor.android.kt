package com.example.tugas6_1231401971.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class AndroidNetworkMonitor(
    private val context: Context
) : NetworkMonitor {
    override val isConnected: Flow<Boolean> = callbackFlow {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                launch { send(true) }
            }

            override fun onLost(network: Network) {
                launch { send(false) }
            }
        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(request, callback)

        val currentState = connectivityManager.activeNetwork?.let {
            connectivityManager.getNetworkCapabilities(it)
        }?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false
        
        send(currentState)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }.distinctUntilChanged()
}

// We will use Koin to provide the Context, so the factory function might need to be adjusted or we use a different approach for Koin.
// Since the expect/actual fun getNetworkMonitor() doesn't take parameters, we might need to handle context differently if we want to keep this signature.
// Better to inject it via Koin in the module.
actual fun getNetworkMonitor(): NetworkMonitor {
    // This is tricky without context here if we use this exact signature.
    // I'll adjust the plan to use Koin more effectively.
    throw UnsupportedOperationException("Use Koin to inject NetworkMonitor")
}
