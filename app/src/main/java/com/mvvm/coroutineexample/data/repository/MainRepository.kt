package com.mvvm.coroutineexample.data.repository

import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.CharacterModel
import com.mvvm.coroutineexample.util.asViewModel

class MainRepository(
    private val apiHelper: ApiHelper,
    private val characterDao: CharacterDao
){
    suspend fun getCharacters() : List<CharacterModel>{
        return apiHelper.getCharacters().asViewModel()
    }
}