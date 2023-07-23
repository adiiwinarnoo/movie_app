package com.example.testteravinapplication.network

import com.example.testteravinapplication.data.model.ResponseMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie")
    fun getMovie(
        @Query("api_key") apiKey : String = "f7b67d9afdb3c971d4419fa4cb667fbf"
    ) : Call<ResponseMovie>

}