package com.example.testteravinapplication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testteravinapplication.data.model.ResponseMovie
import com.example.testteravinapplication.network.repository.MovieRepository

class MovieViewModel : ViewModel() {

    var repository = MovieRepository()
    var liveData = MutableLiveData<ResponseMovie>()

    fun getMovie() : MutableLiveData<ResponseMovie>{
        repository.getMovie(){
            liveData.postValue(it)
        }
        return liveData
    }
}