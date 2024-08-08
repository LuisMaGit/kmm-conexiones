package com.luisma.conexiones.platform

interface CommonLUtils {
    fun getBatteryLevel(): String
}

expect fun getLUtils(): CommonLUtils


class LUtilsAccess {
    fun androidOrIOSBatteryLevel(): String {
        return "this is the logic ${getLUtils().getBatteryLevel()}"
    }
}