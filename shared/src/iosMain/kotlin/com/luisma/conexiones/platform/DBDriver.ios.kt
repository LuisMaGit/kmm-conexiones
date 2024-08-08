package com.luisma.conexiones.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.shared.db.ConexionesDatabase

actual fun conSqlDriver(): SqlDriver {
    return NativeSqliteDriver(
        schema = ConexionesDatabase.Schema,
        name = "conexiones.db"
    )
}