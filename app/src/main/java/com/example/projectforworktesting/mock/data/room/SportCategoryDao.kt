package com.example.projectforworktesting.mock.data.room


import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Dao

@Dao
interface SportCategoryDao {

    @Query("select * from SportCategoryData")
    fun getAll(): List<SportCategoryData>

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: SportCategoryData)
}