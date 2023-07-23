package com.example.testteravinapplication.network

import com.example.testteravinapplication.data.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var server : ApiService = retrofit.create(ApiService::class.java)
}