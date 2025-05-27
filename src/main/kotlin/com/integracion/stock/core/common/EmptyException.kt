package com.integracion.com.integracion.stock.core.common

class EmptyException(message: String) : RuntimeException(message)

fun emptyError(message: Any): Nothing = throw EmptyException(message.toString())