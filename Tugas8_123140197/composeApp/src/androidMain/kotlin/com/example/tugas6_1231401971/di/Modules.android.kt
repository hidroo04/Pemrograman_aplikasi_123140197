package com.example.tugas6_1231401971.di

import com.example.notes.db.DatabaseDriverFactory
import com.example.tugas6_1231401971.util.AndroidDeviceInfo
import com.example.tugas6_1231401971.util.AndroidNetworkMonitor
import com.example.tugas6_1231401971.util.DeviceInfo
import com.example.tugas6_1231401971.util.NetworkMonitor
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(get()) }
    single { get<DatabaseDriverFactory>().createDriver() }
    single<DeviceInfo> { AndroidDeviceInfo() }
    single<NetworkMonitor> { AndroidNetworkMonitor(get()) }
}