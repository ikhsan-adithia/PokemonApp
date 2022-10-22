package com.test.pokemonapp.feature_pokemon_list.domain.repository

import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonList
import kotlinx.coroutines.flow.Flow

interface PokemonListRepository {

    fun getPokemonList(): Flow<Result<PokemonList>>

    fun loadNextMorePokemons(url: String): Flow<Result<PokemonList>>
}