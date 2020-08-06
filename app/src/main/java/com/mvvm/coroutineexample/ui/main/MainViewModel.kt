package com.mvvm.coroutineexample.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mvvm.coroutineexample.data.repository.MainRepository
import com.mvvm.coroutineexample.util.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {

    val _characterList =mainRepository.characters

    fun getCharacters() = liveData(Dispatchers.IO) {
        mainRepository.refreshCharacters()
        emit(Resource.loading(null))
        try {
            emit(Resource.success(data = _characterList))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}