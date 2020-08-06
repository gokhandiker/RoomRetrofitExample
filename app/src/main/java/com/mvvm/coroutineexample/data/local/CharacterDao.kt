package com.mvvm.coroutineexample.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm.coroutineexample.data.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(videos : List<CharacterEntity>)

    @Query("select * from characters")
    fun getAllCharacters() : LiveData<List<CharacterEntity>>
}