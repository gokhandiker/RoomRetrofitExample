package com.mvvm.coroutineexample.data.api

import com.mvvm.coroutineexample.data.model.Info

data class ResultResponse<T>(
    val info : Info,
    val results: List<T> = emptyList()
)