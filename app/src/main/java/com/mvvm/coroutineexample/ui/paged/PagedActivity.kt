package com.mvvm.coroutineexample.ui.paged

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.data.api.ApiHelper
import com.mvvm.coroutineexample.data.api.RetrofitBuilder
import com.mvvm.coroutineexample.data.local.AppDatabase
import com.mvvm.coroutineexample.ui.base.ViewModelFactory
import com.mvvm.coroutineexample.ui.main.CharactersAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_paged.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@ExperimentalPagingApi
class PagedActivity : AppCompatActivity() , CharactersAdapter.CharacterItemListener{


    private lateinit var pagedViewModel: PagedViewModel
    private lateinit var appDatabase: AppDatabase
    private lateinit var adapter : PagedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paged)


        appDatabase = AppDatabase.getDatabase(this)


        adapter = PagedAdapter()
        paged_recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        paged_recyclerview.adapter = adapter

        pagedViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(RetrofitBuilder.apiService), appDatabase)
        ).get(PagedViewModel::class.java)


        lifecycleScope.launch {
            pagedViewModel.getCharacters().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun onClickedCharacter(characterId: Long) {
        Log.e("onClickedCharacter: ",""+characterId)
    }
}