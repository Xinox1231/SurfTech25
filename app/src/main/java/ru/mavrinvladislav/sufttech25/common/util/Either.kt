package ru.mavrinvladislav.sufttech25.common.util

sealed class Either<out S, out F> {

    data class Success<out S>(val value: S) : Either<S, Nothing>()

    data class Failure<out F>(val value: F) : Either<Nothing, F>()

    fun isSuccess(): Boolean = this is Success

    fun isFailure(): Boolean = this is Failure

    fun <T> fold(onSuccess: (S) -> T, onFailure: (F) -> T) {
        when (this) {
            is Success -> onSuccess(value)
            is Failure -> onFailure(value)
        }
    }
}