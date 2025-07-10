package com.app.stusmart.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://172.16.0.116:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}