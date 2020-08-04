package com.mvvm.coroutineexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.RetrofitBuilder
import com.mvvm.coroutineexample.ui.base.ViewModelFactory
import com.mvvm.coroutineexample.util.Status

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))
        ).get(MainViewModel::class.java)


        mainViewModel.getCharacters().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> { resource.data?.let { Log.e("observe",it.toString()) }}
                    Status.ERROR -> { Log.e("observe","Error: ${it.message}")}
                    Status.LOADING -> { Log.e("observe","Loading...")}
                }
            }
        })

    }
}