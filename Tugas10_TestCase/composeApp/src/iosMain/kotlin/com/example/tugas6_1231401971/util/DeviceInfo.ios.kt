package com.example.tugas6_1231401971.util

import platform.UIKit.UIDevice

class IosDeviceInfo : DeviceInfo {
    override val model: String = UIDevice.currentDevice.model
    override val osVersion: String = UIDevice.currentDevice.systemVersion
    override val manufacturer: String = "Apple"
}

actual fun getDeviceInfo(): DeviceInfo = IosDeviceInfo()