package com.mvvm.coroutineexample.data.api

import com.mvvm.coroutineexample.data.model.Characters
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("character")
   suspend fun getCharacterList() : Response<Characters>
}