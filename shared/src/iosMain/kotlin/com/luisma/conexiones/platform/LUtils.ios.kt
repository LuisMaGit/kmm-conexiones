package com.luisma.conexiones.platform

import cocoapods.LuismaLib.LUtils
import kotlinx.cinterop.ExperimentalForeignApi

class IOSLUtils : CommonLUtils {
    @OptIn(ExperimentalForeignApi::class)
    override fun getBatteryLevel(): String {
        return LUtils().getBatteryLevel()
    }
}

actual fun getLUtils(): CommonLUtils = IOSLUtils()