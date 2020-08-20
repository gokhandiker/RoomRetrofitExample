package com.mvvm.coroutineexample.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.paging.ExperimentalPagingApi
import com.mvvm.coroutineexample.R
import com.mvvm.coroutineexample.ui.paged.PagedActivity
import com.mvvm.coroutineexample.ui.standartlist.StandartListActivity
import kotlinx.android.synthetic.main.activity_main.*

@ExperimentalPagingApi
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        main_btn_standart.setOnClickListener {
            val intent = Intent(this, StandartListActivity::class.java)
            startActivity(intent)
        }

        main_button_paginated.setOnClickListener {
            val intent = Intent(this, PagedActivity::class.java)
            startActivity(intent)
        }
    }
}