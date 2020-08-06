package com.mvvm.coroutineexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.CharacterModel
import com.mvvm.coroutineexample.util.asEntitiy
import com.mvvm.coroutineexample.util.asViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(
    private val apiHelper: ApiHelper,
    private val characterDao: CharacterDao
){

    val characters : LiveData<List<CharacterModel>> = Transformations.map(characterDao.getAllCharacters()){
        it.asViewModel()
    }

    suspend fun refreshCharacters() {
        withContext(Dispatchers.IO){
            val characterList = apiHelper.getCharacters()
            characterDao.insertAll(characterList.asEntitiy())
        }
    }


}