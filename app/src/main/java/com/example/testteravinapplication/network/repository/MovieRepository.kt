package com.example.testteravinapplication.network.repository

import android.util.Log
import com.example.testteravinapplication.data.model.ResponseMovie
import com.example.testteravinapplication.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieRepository {

    var apiConfig = ApiConfig()

    fun getMovie(onResult: (result: ResponseMovie) -> Unit){
        apiConfig.server.getMovie().enqueue(object : Callback<ResponseMovie>{
            override fun onResponse(call: Call<ResponseMovie>, response: Response<ResponseMovie>) {
                Log.d("GET-MOVIE-FAILED", "onResponse: ${response.code()}, ${response.message()}")
                if (response.isSuccessful && response.body()?.results != null){
                    getMovieSuccess(response, onResult)
                }else if (response.code() == 401){
                    val messageError = "Api Key salah!!"
                    val default = ResponseMovie(message = messageError)
                    onResult(default)
                }
            }

            override fun onFailure(call: Call<ResponseMovie>, t: Throwable) {
                Log.d("GET-MOVIE-FAILED", "onResponse-tmesage: ${t.message}")
                val messageError = "Periksa internet anda!!!"
                val default = ResponseMovie(message = messageError)
                onResult(default)
            }

        })
    }

    fun getMovieSuccess(response: Response<ResponseMovie>, onResult: (result: ResponseMovie) -> Unit){
        Log.d("GET-MOVIE-FAILED", "onResponse-T: ${response.code()}")
        when (response.code()){
            200 -> {
                onResult(response.body()!!)
            }
            400 ->{
                val messageError = "Ada yang salah!"
                val default = ResponseMovie(message = messageError)
                onResult(default)
            }
            401 ->{
                val messageError = "Api Key salah!!"
                val default = ResponseMovie(message = messageError)
                onResult(default)
            }
            else -> {
                val messageError = "Unknown error"
                val default = ResponseMovie(message = messageError)
                onResult(default)
            }
        }
    }



}