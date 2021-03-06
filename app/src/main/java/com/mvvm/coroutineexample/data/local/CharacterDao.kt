package com.mvvm.coroutineexample.data.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
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

    @Query("SELECT * FROM characters")
    fun getAllCharacters() : LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM characters")
    fun getAllCharactersPaged(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters")
    fun clearAll()
}