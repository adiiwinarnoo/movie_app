package com.example.testteravinapplication.ui.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testteravinapplication.adapter.MovieAdapter
import com.example.testteravinapplication.data.localDB.MovieDao
import com.example.testteravinapplication.data.localDB.MovieLocal
import com.example.testteravinapplication.data.localDB.RoomDatabaseItem
import com.example.testteravinapplication.data.service.MovieService
import com.example.testteravinapplication.data.utils.Constant
import com.example.testteravinapplication.databinding.ActivityMainBinding
import com.example.testteravinapplication.ui.viewmodel.MovieViewModel
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var adapterMovie : MovieAdapter
    lateinit var viewModelMovie : MovieViewModel
    lateinit var movieDao: MovieDao
    lateinit var roomDB : RoomDatabaseItem
    private var alertDialog: AlertDialog? = null
    private var isNotifShown = false

    private val dataUpdatedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (!isNotifShown) {
                showNotif()
                isNotifShown = true
            }
            movieDao.delete()
            getMovie()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        roomDB = RoomDatabaseItem.getDatabase(this)
        movieDao = roomDB.dao()


        viewModelMovie = ViewModelProvider(this).get(MovieViewModel::class.java)

        var apiKey = "f7b67d9afdb3c971d4419fa4cb667f"
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        getMovie()

        startService(Intent(this,MovieService::class.java))

        viewModelMovie.liveData.observe(this){
            val shuffledMovies = it.results?.shuffled()
            val itemMovie = shuffledMovies?.take(10)

            Log.d("DATA-RANDOM", "onCreate: ${itemMovie.toString()}")

            for (i in itemMovie!!.indices){
                val randomId = Random.nextLong()
                movieDao.insertMovie(MovieLocal(randomId,itemMovie[i]!!.originalTitle!!,itemMovie[i]!!.releaseDate!!))
            }


            var dataLocal = movieDao.getAll()
            adapterMovie = MovieAdapter(dataLocal)
            binding.recyclerView.adapter = adapterMovie
        }
    }

    fun getMovie(){
        viewModelMovie.getMovie()
    }

    override fun onStart() {
        super.onStart()
//        val filter = IntentFilter(Constant.ACTION_DATA_UPDATED)
//        LocalBroadcastManager.getInstance(this).registerReceiver(dataUpdatedReceiver, filter)

    }

    override fun onPause() {
        super.onPause()
//        LocalBroadcastManager.getInstance(this).unregisterReceiver(dataUpdatedReceiver)
        val filter = IntentFilter(Constant.ACTION_DATA_UPDATED)
        LocalBroadcastManager.getInstance(this).registerReceiver(dataUpdatedReceiver, filter)
    }

    private fun showNotif() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pembaruan Data Movie")
        builder.setMessage("data movie berhasil diperbarui")
        builder.setCancelable(false)
        builder.setPositiveButton("Lihat") { _, _ ->
            alertDialog?.dismiss()
            isNotifShown = false
        }
        alertDialog = builder.create()
        alertDialog?.show()

    }
}