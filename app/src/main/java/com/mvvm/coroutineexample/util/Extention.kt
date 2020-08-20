package com.mvvm.coroutineexample.util

import com.mvvm.coroutineexample.data.entities.CharacterEntity
import com.mvvm.coroutineexample.data.model.CharacterApi
import com.mvvm.coroutineexample.data.model.Characters
import com.mvvm.coroutineexample.ui.CharacterModel

fun CharacterEntity.asDomainModel() = CharacterModel(
    id = id,
    name = name
)

/**
 * Convert Network results to database objects
 */
fun Characters.asEntitiy(): List<CharacterEntity> {
    return results.map {
        CharacterEntity(
            id = it.id,
            name = it.name,
            status = it.status,
            created = it.created,
            gender = it.gender,
            location = it.location,
            c_image = it.image,
            origin = it.origin,
            species = it.species,
            type = it.type,
            url = it.url
        )
    }
}

fun Characters.asViewModel(): List<CharacterModel> {
    return results.map {
        CharacterModel(
            id = it.id,
            name = it.name
        )
    }
}

fun List<CharacterEntity>.asViewModel(): List<CharacterModel> {
    return map {
        CharacterModel(
            id = it.id,
            name = it.name
        )
    }
}

fun List<CharacterApi>.asEntitiy() : List<CharacterEntity>{
    return map {
        CharacterEntity(
            id = it.id.toLong(),
            name = it.name,
            status = it.status,
            created = it.created,
            gender = it.gender,
            location = it.location,
            c_image = it.image,
            origin = it.origin,
            species = it.species,
            type = it.type,
            url = it.url
        )
    }
}