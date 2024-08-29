package com.luisma.conexiones.services_db

import com.luisma.conexiones.platform.conSqlDriver
import com.shared.db.ConexionesDatabase

object DBInstance {
    val db = ConexionesDatabase(conSqlDriver())
}