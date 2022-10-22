package com.test.pokemonapp.feature_pokemon_list.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonItem(
    @PrimaryKey
    val name: String,
    val sprite: String,
    val nickname: String? = null
)
