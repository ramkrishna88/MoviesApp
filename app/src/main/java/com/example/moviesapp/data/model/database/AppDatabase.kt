package com.example.moviesapp.data.model.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieLocal::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}