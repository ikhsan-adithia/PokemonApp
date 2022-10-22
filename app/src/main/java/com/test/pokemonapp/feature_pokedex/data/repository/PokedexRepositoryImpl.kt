package com.test.pokemonapp.feature_pokedex.data.repository

import com.test.pokemonapp.core.data.local.CaughtPokemonDao
import com.test.pokemonapp.feature_pokedex.domain.repository.PokedexRepository
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import kotlinx.coroutines.flow.Flow

class PokedexRepositoryImpl(
    private val dao: CaughtPokemonDao
): PokedexRepository {

    override fun getAllCaughtPokemon(): Flow<List<PokemonItem>> =
        dao.getCaughtPokemon()
}