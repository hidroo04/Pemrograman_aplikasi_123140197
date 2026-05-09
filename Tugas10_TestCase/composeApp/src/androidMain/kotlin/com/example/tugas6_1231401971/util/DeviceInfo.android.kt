package com.example.tugas6_1231401971.util

import android.os.Build

class AndroidDeviceInfo : DeviceInfo {
    override val model: String = Build.MODEL
    override val osVersion: String = Build.VERSION.RELEASE
    override val manufacturer: String = Build.MANUFACTURER
}

actual fun getDeviceInfo(): DeviceInfo = AndroidDeviceInfo()