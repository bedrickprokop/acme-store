package com.acmestore.model

import android.os.SystemClock
import com.acmestore.Consts
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockClient : Interceptor {

    val GET_USER_ENDPOINT = "/acmestore/user/1/CD344689F90"
    val GET_USER_RESPONSE =
        """{"id":1,"name":"Bedrick Prokop","email":"bedrick@mymail.com", "money": 1000000.00, "products":[]}"""

    val GET_PRODUCTS_ENDPOINT = "/acmestore/product?token=CD344689F90"
    val GET_PRODUCTS_RESPONSE = """[{"id": 1, "name": "Test", "unitPrice": 100.00, "description": "Test of description"}]"""

    val CONTENT_TYPE_KEY = "content-type"
    val CONTENT_TYPE_VALUE = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val data = when (url.encodedPath) {
            GET_USER_ENDPOINT -> GET_USER_RESPONSE
            GET_PRODUCTS_ENDPOINT -> GET_PRODUCTS_RESPONSE
            else -> Consts.EMPTY
        }

        val response = Response.Builder()
            .code(200)
            .message(data)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                data.toResponseBody(CONTENT_TYPE_VALUE.toMediaTypeOrNull())
                /*ResponseBody.create(
                    MediaType.parse(CONTENT_TYPE_VALUE), data.toByteArray()
                )*/
            )
            .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
            .build()

        //Create a delay here just for testing the progress on the screen
        SystemClock.sleep(1500);

        return response
    }
}