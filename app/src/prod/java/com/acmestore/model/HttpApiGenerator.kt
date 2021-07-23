package com.acmestore.model

import com.acmestore.Consts.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HttpApiGenerator<T> {

    fun gen(clazz: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(clazz)
    }
}