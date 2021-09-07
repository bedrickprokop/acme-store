package com.acmestore.model

import android.os.SystemClock
import com.acmestore.Consts
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

class MockClient : Interceptor {

    val GET_USER_ENDPOINT = "/acmestore/user/1/CD344689F90"
    val GET_USER_RESPONSE =
        """{"id":1,"name":"Bedrick Prokop","email":"bedrick@mymail.com", "money": 1000000.00, "products":[]}"""

    val GET_USER_INVENTORY_ENDPOINT = "/acmestore/user/1/inventory/CD344689F90"
    val GET_USER_INVENTORY_RESPONSE =
        """[{"id": 1, "name": "Disintegrating Pistol", "unitPrice": 100.00, "description": "Disintegrating Pistol"},{"id": 2, "name": "Super Speed Vitamins", "unitPrice": 55.10, "description": "Super Speed Vitamins"}]"""

    val GET_PRODUCTS_ENDPOINT = "/acmestore/product/CD344689F90"
    val GET_PRODUCTS_RESPONSE =
        """[{"id": 1, "name": "Disintegrating Pistol", "unitPrice": 100.00, "description": "Disintegrating Pistol"},{"id": 2, "name": "Super Speed Vitamins", "unitPrice": 55.10, "description": "Super Speed Vitamins"},{"id": 3, "name": "Jet Propelled Tennis Shoes Fleet Foot", "unitPrice": 988.90, "description": "Jet Propelled Tennis Shoes Fleet Foot"},{"id": 4, "name": "Glass Cutter", "unitPrice": 32.01, "description": "Glass Cutter"}]"""

    val POST_PRODUCT_ADD_CART_ENDPOINT = "/acmestore/product/addcart"
    val POST_PRODUCT_ADD_CART_RESPONSE =
        """{"id": 4, "name": "Glass Cutter", "unitPrice": 32.01, "description": "Glass Cutter", "owner": {"id": 1}}"""

    val POST_PRODUCT_BUY_ENDPOINT = "/acmestore/product/buy"
    val POST_PRODUCT_BUY_RESPONSE = """[2,4,5]"""

    val POST_PRODUCT_SELL_ENDPOINT = "/acmestore/product/sell"
    val POST_PRODUCT_SELL_RESPONSE =
        """{"id": 1, "name": "Disintegrating Pistol", "unitPrice": 100.00, "description": "Disintegrating Pistol", "owner": {"id": 1}}"""

    val CONTENT_TYPE_KEY = "content-type"
    val CONTENT_TYPE_VALUE = "application/json"

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        val data = when (url.encodedPath) {
            GET_USER_ENDPOINT -> GET_USER_RESPONSE
            GET_USER_INVENTORY_ENDPOINT -> GET_USER_INVENTORY_RESPONSE
            GET_PRODUCTS_ENDPOINT -> GET_PRODUCTS_RESPONSE
            POST_PRODUCT_ADD_CART_ENDPOINT -> POST_PRODUCT_ADD_CART_RESPONSE
            POST_PRODUCT_BUY_ENDPOINT -> POST_PRODUCT_BUY_RESPONSE
            POST_PRODUCT_SELL_ENDPOINT -> POST_PRODUCT_SELL_RESPONSE
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
        SystemClock.sleep(500);

        return response
    }
}