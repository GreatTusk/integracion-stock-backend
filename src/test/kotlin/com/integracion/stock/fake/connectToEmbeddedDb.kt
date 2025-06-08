package com.integracion.com.integracion.stock.fake

import java.sql.Connection
import java.sql.DriverManager

fun connectToEmbeddedDb(): Connection =
    DriverManager.getConnection(
        /* url = */ "jdbc:h2:mem:test;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        /* user = */ "root",
        /* password = */ ""
    )