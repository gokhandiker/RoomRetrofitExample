package com.mvvm.coroutineexample.data.api

class ApiHelper( private val apiService: ApiService){
    suspend fun getCharacters() = apiService.getCharacters()

}