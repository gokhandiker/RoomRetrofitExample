package com.mvvm.coroutineexample.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.MortyRemoteMediator
import com.mvvm.coroutineexample.data.entities.CharacterEntity
import com.mvvm.coroutineexample.data.local.AppDatabase
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class PagedRepository(
    private val api: ApiHelper,
    private val database: AppDatabase
) {


    fun getCharacters(): Flow<PagingData<CharacterEntity>> {

        val pagingSourceFactory = { database.characterDao().getAllCharactersPaged() }

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true),
            remoteMediator = MortyRemoteMediator(
                database, api
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }

}
