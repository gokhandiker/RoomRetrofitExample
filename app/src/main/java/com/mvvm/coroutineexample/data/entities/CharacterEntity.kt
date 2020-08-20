package com.mvvm.coroutineexample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
data class CharacterEntity(
    val id: Long,
    var name : String,
    var status : String,
    var species : String,
    var type : String,
    var gender : String,
    @Embedded
    var origin: Origin,
    @Embedded
    var location: Location,
    var url : String,
    var created : String,
    @ColumnInfo(name = "character_image")
    var c_image : String

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    var characterId: Int = 0


}