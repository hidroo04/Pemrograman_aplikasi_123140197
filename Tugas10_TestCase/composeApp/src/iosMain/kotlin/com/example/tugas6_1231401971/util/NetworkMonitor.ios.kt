package com.example.tugas6_1231401971.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import platform.Network.*
import platform.darwin.dispatch_get_main_queue

class IosNetworkMonitor : NetworkMonitor {
    private val _isConnected = MutableStateFlow(true)
    override val isConnected: Flow<Boolean> = _isConnected

    private val monitor = nw_path_monitor_create()

    init {
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            _isConnected.value = status == nw_path_status_satisfied
        }
        nw_path_monitor_start(monitor)
    }
}

actual fun getNetworkMonitor(): NetworkMonitor = IosNetworkMonitor()