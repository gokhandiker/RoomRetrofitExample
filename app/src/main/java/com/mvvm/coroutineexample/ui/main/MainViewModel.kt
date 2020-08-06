package com.mvvm.coroutineexample.ui.main

import androidx.lifecycle.ViewModel
import com.mvvm.coroutineexample.data.repository.MainRepository
import com.mvvm.coroutineexample.ui.CharacterModel


class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {


    val characters = mainRepository.getCharacters()



}