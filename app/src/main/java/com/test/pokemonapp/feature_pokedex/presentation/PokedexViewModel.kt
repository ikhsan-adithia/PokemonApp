package com.test.pokemonapp.feature_pokedex.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pokemonapp.feature_pokedex.domain.repository.PokedexRepository
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val repository: PokedexRepository
): ViewModel() {

    private val _state = MutableStateFlow<List<PokemonItem>>(emptyList())
    val state get(): StateFlow<List<PokemonItem>> = _state

    init {
        getAllCaughtPokemon()
    }

    private fun getAllCaughtPokemon() {
        repository.getAllCaughtPokemon()
            .onEach { pokemons ->
                _state.update { pokemons }
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }
}