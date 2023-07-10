package com.example.projectforworktesting.mock.data.room


import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SportCategoryData::class], version = 1)
abstract class DbConnection : RoomDatabase() {
    abstract fun entityDao(): SportCategoryDao
}