package com.mvvm.coroutineexample.data.api

import com.mvvm.coroutineexample.data.model.CharacterApi
import com.mvvm.coroutineexample.data.model.Characters
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("character")
   suspend fun getCharacterList() : Response<Characters>

    @GET("character")
    suspend fun getCharacterListPaged(@Query("page") page : Int) : Response<ResultResponse<CharacterApi>>
}