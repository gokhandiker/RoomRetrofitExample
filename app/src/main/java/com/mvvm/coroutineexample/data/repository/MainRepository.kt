package com.mvvm.coroutineexample.data.repository

import com.mvvm.coroutineexample.data.api.ApiHelper

class MainRepository(
    private val apiHelper: ApiHelper
){
    suspend fun getCharacters() = apiHelper.getCharacters()
}