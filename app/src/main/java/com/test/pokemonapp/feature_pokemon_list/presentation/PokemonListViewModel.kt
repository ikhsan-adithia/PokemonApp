package com.test.pokemonapp.feature_pokemon_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonList
import com.test.pokemonapp.feature_pokemon_list.domain.repository.PokemonListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonListRepository
): ViewModel() {

    private val _state = MutableStateFlow(PokemonListState())
    val state get(): StateFlow<PokemonListState> = _state

    init {
        getPokemonList()
    }

    fun loadNextPage() {
        if (!state.value.isLoading) {
            state.value.nextPageUrl?.let { url ->
                repository.loadNextMorePokemons(url).onEachResult(true)
            }
        }
    }

    private fun getPokemonList() {
        if (!state.value.isLoading) {
            repository.getPokemonList().onEachResult()
        }
    }

    private fun Flow<Result<PokemonList>>.onEachResult(isLoadNextPage: Boolean = false) {
        this.onEach { result ->
            when (result) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            nextPageUrl = result.data?.next,
                            isLoading = false,
                            pokemons = if (isLoadNextPage) {
                                it.pokemons + (result.data?.pokemons ?: emptyList())
                            } else {
                                result.data?.pokemons ?: emptyList()
                            }
                        )
                    }
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false) }
                }
                is Result.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.launchIn(viewModelScope + Dispatchers.IO)
    }
}