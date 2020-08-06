package com.mvvm.coroutineexample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    val name : String,
    val status : String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var characterId: Int = 0
}