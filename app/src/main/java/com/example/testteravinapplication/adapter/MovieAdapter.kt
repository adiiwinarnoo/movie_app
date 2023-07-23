package com.example.testteravinapplication.adapter

import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testteravinapplication.R
import com.example.testteravinapplication.data.localDB.MovieLocal
import com.example.testteravinapplication.data.model.ResponseMovie
import com.example.testteravinapplication.data.model.ResultsItem

class MovieAdapter(val models : List<MovieLocal>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder( itemView : View) : RecyclerView.ViewHolder(itemView) {
        var tittleName = itemView.findViewById<TextView>(R.id.tv_tittle_movie)
        var releaseDate = itemView.findViewById<TextView>(R.id.tv_release_movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tittleName.text = models?.get(position)?.originalTittle
        holder.releaseDate.text = models?.get(position)?.releaseDate
    }

    override fun getItemCount(): Int {
        return models!!.size
    }
}