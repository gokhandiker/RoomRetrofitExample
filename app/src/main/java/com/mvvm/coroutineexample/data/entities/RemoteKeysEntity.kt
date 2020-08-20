package com.mvvm.coroutineexample.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RemoteKeysEntity(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)