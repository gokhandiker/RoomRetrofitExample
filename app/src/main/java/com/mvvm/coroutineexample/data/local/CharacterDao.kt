package com.mvvm.coroutineexample.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mvvm.coroutineexample.data.entities.Character

@Dao
interface CharacterDao {

    @Insert
    fun insertCharacter(character: Character)

    @Query("select * from characters")
    fun getAllCharacters() : List<Character>
}