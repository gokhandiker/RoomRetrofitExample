package com.mvvm.coroutineexample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.ExperimentalPagingApi
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.data.repository.MainRepository
import com.mvvm.coroutineexample.data.repository.PagedRepository
import com.mvvm.coroutineexample.ui.standartlist.StandartViewModel
import com.mvvm.coroutineexample.ui.paged.PagedViewModel
import java.lang.IllegalArgumentException
@ExperimentalPagingApi
class ViewModelFactory(
    private val apiHelper: ApiHelper,
    private val database: AppDatabase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(StandartViewModel::class.java)) {
            return StandartViewModel(MainRepository(apiHelper,database.characterDao())) as T
        }

        if (modelClass.isAssignableFrom(PagedViewModel::class.java)) {
            return PagedViewModel(PagedRepository(apiHelper,database)) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }

}