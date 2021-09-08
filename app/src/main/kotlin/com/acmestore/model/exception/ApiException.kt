package com.acmestore.model.exception

open class ApiException(
    message: String,
    val statusCode: Int,
    cause: Throwable?
) : Exception(message, cause)
