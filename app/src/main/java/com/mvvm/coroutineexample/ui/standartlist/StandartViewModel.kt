package com.mvvm.coroutineexample.ui.standartlist

import androidx.lifecycle.ViewModel
import com.mvvm.coroutineexample.data.repository.MainRepository

class StandartViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {


    val characters = mainRepository.getCharacters()



}