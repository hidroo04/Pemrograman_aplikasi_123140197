package com.example.tugas6_1231401971.util

import kotlinx.coroutines.flow.Flow

interface NetworkMonitor {
    val isConnected: Flow<Boolean>
}

expect fun getNetworkMonitor(): NetworkMonitor