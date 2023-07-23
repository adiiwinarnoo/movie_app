package com.example.testteravinapplication.data.localDB

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "TableMovie")
@Parcelize
data class MovieLocal (

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long = 0,

    @ColumnInfo(name = "original_title")
    val originalTittle : String = "",

    @ColumnInfo(name = "release_date")
    val releaseDate : String = "",

) : Parcelable