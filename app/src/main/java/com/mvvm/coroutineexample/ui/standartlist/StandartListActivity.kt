package com.mvvm.coroutineexample.ui.standartlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.RetrofitBuilder
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.data.local.CharacterDao
import com.mvvm.coroutineexample.ui.base.ViewModelFactory
import com.mvvm.coroutineexample.ui.standartlist.StandartViewModel
import com.mvvm.coroutineexample.util.Status
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.Observer
import androidx.paging.ExperimentalPagingApi
import com.mvvm.coroutineexample.util.asViewModel
import kotlinx.android.synthetic.main.activity_standart_list.*

@ExperimentalPagingApi
class StandartListActivity : AppCompatActivity(), CharactersAdapter.CharacterItemListener {

    lateinit var standartViewModel: StandartViewModel
    lateinit var appDatabase : AppDatabase
    lateinit var characterDao : CharacterDao
    lateinit var adapter: CharactersAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standart_list)


        adapter = CharactersAdapter(this)
        standart_recycler.layoutManager = LinearLayoutManager(applicationContext)
        standart_recycler.adapter = adapter

        appDatabase = AppDatabase.getDatabase(this)
        characterDao = appDatabase.characterDao()


        standartViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService),appDatabase)
        ).get(StandartViewModel::class.java)



        standartViewModel.characters.observe(this, Observer { it ->
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> { resource.data?.let {
                        Log.e("observe",it.toString())
                        standart_progress.visibility = View.GONE
                        if (!it.isNullOrEmpty()) adapter.setItems(ArrayList(it.asViewModel()))

                    }}
                    Status.ERROR -> { Log.e("observe","Error: ${it.message}")}
                    Status.LOADING -> { Log.e("observe","Loading...")
                        standart_progress.visibility = View.VISIBLE }
                }
            }
        })

    }

    override fun onClickedCharacter(characterId: Long) {
        Log.e("onClickedCharacter: ",""+characterId)
    }
}