package com.mvvm.coroutineexample.ui

data class CharacterModel(
    val id: Long,
    val name: String,
    val imageUri : String,
    val status : String,
    val species : String,
    val gender : String
)