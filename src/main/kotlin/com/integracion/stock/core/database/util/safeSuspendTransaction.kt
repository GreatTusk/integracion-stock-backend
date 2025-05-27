package com.integracion.com.integracion.stock.core.database.util

import com.integracion.com.integracion.stock.core.common.Result
import com.integracion.com.integracion.stock.core.common.DataError
import com.integracion.com.integracion.stock.core.common.EmptyException
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> safeSuspendTransaction(block: Transaction.() -> T): Result<T, DataError.Remote> = try {
    Result.Success(newSuspendedTransaction(Dispatchers.IO, statement = block))
} catch (e: EmptyException) {
    Result.Empty
} catch (e: ExposedSQLException) {
    Result.Error(DataError.Remote.LOGICAL)
} catch (e: Exception) {
    e.printStackTrace()
    Result.Error(DataError.Remote.UNKNOWN)
}
