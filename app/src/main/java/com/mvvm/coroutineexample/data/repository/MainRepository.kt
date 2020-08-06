package com.mvvm.coroutineexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.CharacterModel
import com.mvvm.coroutineexample.util.Resource
import com.mvvm.coroutineexample.util.asEntitiy
import com.mvvm.coroutineexample.util.asViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainRepository(
    private val apiHelper: ApiHelper,
    private val characterDao: CharacterDao
){

    suspend fun refreshCharacters() {
        withContext(Dispatchers.IO){
            val characterList = apiHelper.getCharacters()
            characterDao.insertAll(characterList.asEntitiy())
        }
    }


    suspend fun getAllCharactersFromApi(): List<CharacterModel> {
        return apiHelper.getCharacters().asViewModel()
    }


    fun getAllCharacters() = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))

        try {
            emit(Resource.success(data = getAllCharactersFromApi()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}