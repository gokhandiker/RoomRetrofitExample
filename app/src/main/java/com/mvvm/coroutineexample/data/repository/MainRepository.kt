package com.mvvm.coroutineexample.data.repository

import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.util.asEntitiy
import com.mvvm.coroutineexample.util.performGetOperation


class MainRepository(
    private val apiHelper: ApiHelper,
    private val characterDao: CharacterDao
) {


    fun getCharacters() = performGetOperation(
        databaseQuery = { characterDao.getAllCharacters() },
        networkCall = { apiHelper.getCharacters() },
        saveCallResult = { characterDao.insertAll(it.results.asEntitiy()) }
    )
}