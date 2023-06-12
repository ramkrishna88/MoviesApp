package com.example.moviesapp.data.model.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "movie")
@Parcelize
data class MovieLocal(
    @PrimaryKey @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int = 0,
    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    @ColumnInfo(name = "overview")
    val overview: String,
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    val posterPath: String,
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    val releaseDate: String,
    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    val originalLanguage: String,
) : Parcelable