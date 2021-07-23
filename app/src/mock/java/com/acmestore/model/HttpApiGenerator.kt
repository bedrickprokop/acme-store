package com.acmestore.model

import com.acmestore.Consts.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpApiGenerator<T> {

    fun get(clazz: Class<T>) : T {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(MockClient())

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(builder.build())
            .build()
        return retrofit.create(clazz)
    }
}