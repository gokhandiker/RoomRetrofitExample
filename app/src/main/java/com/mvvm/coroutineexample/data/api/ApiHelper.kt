package com.mvvm.coroutineexample.data.api

class ApiHelper( private val apiService: ApiService) : BaseDataSource(){
   suspend fun getCharacters() = getResult { apiService.getCharacterList() }

}