package com.mvvm.coroutineexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.RetrofitBuilder
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.base.ViewModelFactory
import com.mvvm.coroutineexample.util.Status
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var appDatabase : AppDatabase
    lateinit var characterDao : CharacterDao



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appDatabase = AppDatabase.getDatabase(this)
        characterDao = appDatabase.characterDao()


        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService),characterDao)
        ).get(MainViewModel::class.java)


        GlobalScope.launch {

            var characters = characterDao!!.getAllCharacters()

            for (character in characters)
                Log.e("mainkontrol",character.name)
        }

        mainViewModel.getCharacters().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> { resource.data?.let {Log.e("observe",it.toString()) }}
                    Status.ERROR -> { Log.e("observe","Error: ${it.message}")}
                    Status.LOADING -> { Log.e("observe","Loading...")}
                }
            }
        })

    }
}