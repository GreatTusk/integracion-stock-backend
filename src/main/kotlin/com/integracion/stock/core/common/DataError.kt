package com.integracion.com.integracion.stock.core.common

sealed interface DataError : Error {
    enum class Remote : DataError {
        REQUEST_TIMEOUT,
        NOT_FOUND,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        LOGICAL
    }

    enum class Local : DataError {
        LOGICAL,
        DISK_FULL,
        UNKNOWN
    }
}