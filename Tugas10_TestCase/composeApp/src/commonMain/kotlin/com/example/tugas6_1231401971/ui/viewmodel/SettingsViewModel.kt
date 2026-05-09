package com.example.tugas6_1231401971.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugas6_1231401971.util.DeviceInfo

class SettingsViewModel(
    val deviceInfo: DeviceInfo
) : ViewModel() {
    val deviceModel = deviceInfo.model
    val osVersion = deviceInfo.osVersion
    val manufacturer = deviceInfo.manufacturer
}