package com.mvvm.coroutineexample.data.model

import com.mvvm.coroutineexample.data.entities.Location
import com.mvvm.coroutineexample.data.entities.Origin

data class CharacterApi(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)