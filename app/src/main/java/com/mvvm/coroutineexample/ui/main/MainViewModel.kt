package com.mvvm.coroutineexample.ui.main

import androidx.lifecycle.ViewModel
import com.mvvm.coroutineexample.data.repository.MainRepository


class MainViewModel(
    private val mainRepository: MainRepository
) : ViewModel() {


    val characters = mainRepository.getAllCharacters()



}