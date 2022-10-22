package com.test.pokemonapp.feature_pokemon_detail.data.repository

import com.test.pokemonapp.core.data.local.CaughtPokemonDao
import com.test.pokemonapp.core.data.remote.PokemonService
import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_detail.data.models.Pokemon
import com.test.pokemonapp.feature_pokemon_detail.domain.repository.PokemonDetailRepository
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonDetailRepositoryImpl(
    private val service: PokemonService,
    private val dao: CaughtPokemonDao
): PokemonDetailRepository {

    override fun getPokemonDetail(name: String): Flow<Result<Pokemon>> = flow {
        emit(Result.Loading())

        val result = service.getPokemonDetails(name)

        if (result.isSuccessful) {
            result.body()?.let {
                emit(Result.Success(it.toPokemon()))
            }
        } else {
            emit(Result.Error(message = "${result.code()} ${result.message()}"))
        }
    }

    override suspend fun insertCaughtPokemonToPokedex(pokemon: PokemonItem) {
        dao.insert(pokemon)
    }

    override suspend fun deleteCaughtPokemon(name: String) {
        dao.delete(name)
    }
}