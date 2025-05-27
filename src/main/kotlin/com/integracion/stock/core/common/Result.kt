package com.integracion.com.integracion.stock.core.common

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : com.integracion.com.integracion.stock.core.common.Error>(val error: E) :
        Result<Nothing, E>

    data object Empty : Result<Nothing, Nothing>
    data object Loading : Result<Nothing, Nothing>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
        Result.Empty -> Result.Empty
        Result.Loading -> Result.Loading
    }
}

inline fun <T, E : Error, R> Result<Iterable<T>, E>.flatMap(map: (T) -> R): Result<List<R>, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(data.map(map))
        Result.Empty -> Result.Empty
        Result.Loading -> Result.Loading
    }
}

inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }

        else -> this
    }
}

inline fun <T, E : Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Error -> {
            action(error)
            this
        }

        else -> this
    }
}

inline fun <T, E : Error> Result<T, E>.onEmpty(action: () -> Unit): Result<T, E> {
    return when (this) {
        is Result.Empty -> {
            action()
            this
        }

        else -> this
    }
}

fun <T, E : Error> Result<T, E>.takeOrNull(): T? {
    return when (this) {
        is Result.Success -> data
        else -> null
    }
}

fun <T, E : Error> Result<T, E>.takeOrDefault(defaultValue: T): T {
    return when (this) {
        is Result.Success -> data
        else -> defaultValue
    }
}

typealias EmptyResult<E> = Result<Unit, E>