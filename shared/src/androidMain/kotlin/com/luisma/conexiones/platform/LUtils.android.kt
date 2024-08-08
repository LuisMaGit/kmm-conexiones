package com.luisma.conexiones.platform

import android.content.Context
import android.content.Context.BATTERY_SERVICE
import android.os.BatteryManager
import com.luisma.conexiones.application

class AndroidLUtils(private val context: Context) : CommonLUtils {
    override fun getBatteryLevel(): String {
        val batManager = context.getSystemService(BATTERY_SERVICE) as BatteryManager
        return batManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY).toString()
    }
}

actual fun getLUtils(): CommonLUtils = AndroidLUtils(context = application)


