package com.mvvm.coroutineexample.data.api

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.mvvm.coroutineexample.data.entities.CharacterEntity
import com.mvvm.coroutineexample.data.entities.RemoteKeysEntity
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.util.Status
import com.mvvm.coroutineexample.util.asEntitiy
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


private const val GITHUB_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MortyRemoteMediator(
    private val database: AppDatabase,
    private val api: ApiHelper
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: GITHUB_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                if (remoteKeys == null) {
                    // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys == null || remoteKeys.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey
            }

        }


        try {
            val apiResponse = api.getCharactersPaged(page)
            var endOfPaginationReached = true

            if (apiResponse.status.equals(Status.SUCCESS)) {
                val resultResponse =apiResponse.data
                val characters = resultResponse!!.results
                 endOfPaginationReached = characters.isEmpty()

                database.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        database.characterDao().clearAll()
                        database.remoteKeyDao().clearRemoteKeys()
                    }

                    val prevKey = if (page == GITHUB_STARTING_PAGE_INDEX) null else page - 1
                    val nextKey = if (endOfPaginationReached) null else page + 1

                    val keys = characters.map {
                        RemoteKeysEntity(repoId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                    }
                    database.remoteKeyDao().insertAll(keys)
                    database.characterDao().insertAll(characters.asEntitiy())
                }
            }




            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }



    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, CharacterEntity>
    ): RemoteKeysEntity? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                database.remoteKeyDao().remoteKeysRepoId(repoId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                // Get the remote keys of the first items retrieved
                database.remoteKeyDao().remoteKeysRepoId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterEntity>): RemoteKeysEntity? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote keys of the last item retrieved
                database.remoteKeyDao().remoteKeysRepoId(repo.id)
            }
    }


}