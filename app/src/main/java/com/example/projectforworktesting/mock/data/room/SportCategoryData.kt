package com.example.projectforworktesting.mock.data.room


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SportCategoryData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val tittle: String,
    val about: String,
    val img: String,
)

{

    companion object {
        
    }

}