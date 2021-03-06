package com.mvvm.coroutineexample.data.api

class ApiHelper( private val apiService: ApiService) : BaseDataSource(){
   suspend fun getCharactersPaged(next : Int) = getResult { apiService.getCharacterListPaged(next) }
   suspend fun getCharacters() = getResult { apiService.getCharacterList() }
}