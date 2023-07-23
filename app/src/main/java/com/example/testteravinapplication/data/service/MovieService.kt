package com.example.testteravinapplication.data.service
import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.testteravinapplication.data.localDB.MovieDao
import com.example.testteravinapplication.data.localDB.RoomDatabaseItem
import com.example.testteravinapplication.data.utils.Constant
import com.example.testteravinapplication.ui.viewmodel.MovieViewModel

class MovieService : Service() {
    private val handler = Handler()
    lateinit var movieDao: MovieDao
    lateinit var roomDB : RoomDatabaseItem
    lateinit var viewModelMovie : MovieViewModel


    private val updateTask = object : Runnable {
        override fun run() {
            // Implementasi pembaruan data dari API dan penyimpanan ke database
            // Setelah pembaruan, kirimkan pesan Broadcast ke Activity

            val intent = Intent(Constant.ACTION_DATA_UPDATED)
            LocalBroadcastManager.getInstance(this@MovieService).sendBroadcast(intent)

            handler.postDelayed(this, Constant.UPDATE_INTERVAL.toLong())
        }
    }

    override fun onCreate() {
        super.onCreate()
        handler.post(updateTask)
        roomDB = RoomDatabaseItem.getDatabase(applicationContext)
        movieDao = roomDB.dao()
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MovieViewModel::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateTask)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
