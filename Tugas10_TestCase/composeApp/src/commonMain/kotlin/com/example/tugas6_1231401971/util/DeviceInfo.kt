package com.example.tugas6_1231401971.util

interface DeviceInfo {
    val model: String
    val osVersion: String
    val manufacturer: String
}

expect fun getDeviceInfo(): DeviceInfo