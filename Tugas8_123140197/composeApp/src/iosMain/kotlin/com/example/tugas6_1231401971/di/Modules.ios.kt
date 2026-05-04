package com.example.tugas6_1231401971.di

import com.example.notes.db.DatabaseDriverFactory
import com.example.tugas6_1231401971.util.DeviceInfo
import com.example.tugas6_1231401971.util.IosDeviceInfo
import com.example.tugas6_1231401971.util.IosNetworkMonitor
import com.example.tugas6_1231401971.util.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory() }
    single { get<DatabaseDriverFactory>().createDriver() }
    single<DeviceInfo> { IosDeviceInfo() }
    single<NetworkMonitor> { IosNetworkMonitor() }
}