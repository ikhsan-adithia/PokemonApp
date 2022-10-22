package com.test.pokemonapp.feature_pokedex.domain.repository

import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {

    fun getAllCaughtPokemon(): Flow<List<PokemonItem>>
}