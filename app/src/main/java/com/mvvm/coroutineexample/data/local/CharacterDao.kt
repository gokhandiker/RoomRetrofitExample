package com.mvvm.coroutineexample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mvvm.coroutineexample.data.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Insert
    fun insertCharacter(character: CharacterEntity)

    @Query("select * from characters")
    fun getAllCharacters() : List<CharacterEntity>
}