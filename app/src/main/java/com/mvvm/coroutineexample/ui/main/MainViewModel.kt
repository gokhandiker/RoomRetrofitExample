package com.mvvm.coroutineexample.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mvvm.coroutineexample.data.entities.Character
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.data.repository.MainRepository
import com.mvvm.coroutineexample.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(
    private val mainRepository: MainRepository,
    private val characterDao: CharacterDao?
) : ViewModel() {

    fun getCharacters() = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        try {
            var characters = mainRepository.getCharacters()
            for (character in  characters.results)
                characterDao!!.insertCharacter(Character(character.name))

            emit(Resource.success(data = mainRepository.getCharacters()))

        }catch (exception : Exception){
            emit(Resource.error(data = null,message = exception.message ?: "Error Occurred!"))
        }
    }

}