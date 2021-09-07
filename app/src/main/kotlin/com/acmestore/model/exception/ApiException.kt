package com.acmestore.model.exception

import okhttp3.ResponseBody

open class ApiException(
    message: String,
    val statusCode: Int,
    cause: Throwable?,
    val errorBody: ResponseBody?,
) : Exception(message, cause)
