package com.example.testteravinapplication.ui.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.testteravinapplication.databinding.ActivitySplashScrennBinding

class SplashScrennActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashScrennBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScrennBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!isInternetConnected()){
            showNoInternetDialog()
        }else{
            startActivity(Intent(this,MainActivity::class.java))
        }


    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val networkCapabilities = connectivityManager?.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Tidak Ada Koneksi Internet")
        builder.setMessage("Pastikan perangkat Anda terhubung ke internet.")
        builder.setPositiveButton("Tutup Aplikasi") { _, _ ->
            finish()
        }
        builder.setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
    }
}