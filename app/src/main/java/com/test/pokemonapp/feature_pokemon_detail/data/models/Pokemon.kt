package com.test.pokemonapp.feature_pokemon_detail.data.models

import com.test.pokemonapp.feature_pokemon_detail.domain.models.Stat
import com.test.pokemonapp.feature_pokemon_detail.domain.models.Type

data class Pokemon(
    val name: String,
    val nickname: String? = null,
    val sprite: String,
    val types: List<Type>,
    val stats: List<Stat>
)