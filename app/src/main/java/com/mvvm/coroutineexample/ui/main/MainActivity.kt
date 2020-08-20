package com.mvvm.coroutineexample.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.RetrofitBuilder
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.base.ViewModelFactory
import com.mvvm.coroutineexample.util.Status
import com.mvvm.coroutineexample.util.asViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), CharactersAdapter.CharacterItemListener {

    lateinit var mainViewModel: MainViewModel
    lateinit var appDatabase : AppDatabase
    lateinit var characterDao : CharacterDao
    lateinit var adapter: CharactersAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        adapter = CharactersAdapter(this)
        main_recycler.layoutManager = LinearLayoutManager(applicationContext)
        main_recycler.adapter = adapter

        appDatabase = AppDatabase.getDatabase(this)
        characterDao = appDatabase.characterDao()


        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService),appDatabase)
        ).get(MainViewModel::class.java)



        mainViewModel.characters.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> { resource.data?.let {
                        Log.e("observe",it.toString())
                        main_progress.visibility = View.GONE
                        if (!it.isNullOrEmpty()) adapter.setItems(ArrayList(it.asViewModel()))

                    }}
                    Status.ERROR -> { Log.e("observe","Error: ${it.message}")}
                    Status.LOADING -> { Log.e("observe","Loading...")
                        main_progress.visibility = View.VISIBLE }
                }
            }
        })

    }

    override fun onClickedCharacter(characterId: Long) {
        Log.e("onClickedCharacter: ",""+characterId)
    }
}