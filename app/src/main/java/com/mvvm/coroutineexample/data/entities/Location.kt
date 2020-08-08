package com.mvvm.coroutineexample.data.entities

import androidx.room.ColumnInfo

data class Location(
    @ColumnInfo(name = "location_name")
    val name : String,
    @ColumnInfo(name = "location_url")
    val url : String
)