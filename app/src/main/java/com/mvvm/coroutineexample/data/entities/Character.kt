package com.mvvm.coroutineexample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class Character(
    val name : String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}