package com.app.stusmart.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: StudentApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/") // Đổi thành IP LAN nếu chạy trên thiết bị thật
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StudentApi::class.java)
    }
}