package com.luisma.conexiones.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.luisma.conexiones.application
import com.shared.db.ConexionesDatabase

class DatabaseDriver(private val context: Context) {
    fun sqlDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ConexionesDatabase.Schema,
            context = context,
            name = "conexiones.db"
        )
    }
}

actual fun conSqlDriver(): SqlDriver =
    DatabaseDriver(context = application).sqlDriver()