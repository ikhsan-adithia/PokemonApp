package com.test.pokemonapp.feature_pokemon_detail.domain.repository

import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_detail.data.models.Pokemon
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokemonDetailRepository {

    fun getPokemonDetail(name: String): Flow<Result<Pokemon>>

    suspend fun insertCaughtPokemonToPokedex(pokemon: PokemonItem)

    suspend fun deleteCaughtPokemon(name: String)
}