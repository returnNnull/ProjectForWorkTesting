package com.example.projectforworktesting.mock.data.room


import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Dao

@Dao
interface SportFactsDao {

    @Query("select * from SportFactData")
    fun getAll(): List<SportFactData>

    @Insert(onConflict = REPLACE)
    suspend fun insert(entity: SportFactData)

    @Query("select * from SportFactData where id = :id")
    fun getById(id: Int): SportFactData
}