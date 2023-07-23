package com.example.testteravinapplication.data.localDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(item: MovieLocal)

    @Query("DELETE FROM TableMovie")
    fun delete()

    @Query("SELECT * FROM TableMovie")
    fun getAll() : List<MovieLocal>

}