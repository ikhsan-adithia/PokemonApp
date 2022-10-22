package com.test.pokemonapp.feature_pokemon_list.data.repository

import com.test.pokemonapp.core.data.remote.PokemonService
import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonList
import com.test.pokemonapp.feature_pokemon_list.domain.repository.PokemonListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokemonListRepositoryImpl(
    private val service: PokemonService
): PokemonListRepository {

    override fun getPokemonList(): Flow<Result<PokemonList>> = flow {
        emit(Result.Loading())

        val result = service.getPokemonList()

        if (result.isSuccessful) {
            result.body()?.let {
                emit(Result.Success(it.toPokemonList()))
            }
        } else {
            emit(Result.Error(message = "${result.code()} ${result.message()}"))
        }
    }

    override fun loadNextMorePokemons(url: String): Flow<Result<PokemonList>> = flow {
        emit(Result.Loading())

        val result = service.loadMorePokemonList(url)

        if (result.isSuccessful) {
            result.body()?.let {
                emit(Result.Success(it.toPokemonList()))
            }
        } else {
            emit(Result.Error(message = "${result.code()} ${result.message()}"))
        }
    }
}