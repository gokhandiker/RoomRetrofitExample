package com.mvvm.coroutineexample.ui.paged

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mvvm.coroutineexample.data.repository.PagedRepository
import com.mvvm.coroutineexample.ui.CharacterModel
import com.mvvm.coroutineexample.util.asDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class PagedViewModel(
    private val pagedRepository: PagedRepository
) : ViewModel() {

    private var currentList: Flow<PagingData<CharacterModel>>? = null


    fun getCharacters(): Flow<PagingData<CharacterModel>> {

        val characters: Flow<PagingData<CharacterModel>> = pagedRepository.getCharacters()
            .map { pagingData -> pagingData.map { it.asDomainModel() } }
            .cachedIn(viewModelScope)

        currentList = characters
        return characters

    }
}