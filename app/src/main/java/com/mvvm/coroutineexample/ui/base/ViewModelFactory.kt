package com.mvvm.coroutineexample.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.repository.MainRepository
import com.mvvm.coroutineexample.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class ViewModelFactory(
    private val apiHelper: ApiHelper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw  IllegalArgumentException("Unknown class name")
    }

}