package com.test.pokemonapp.feature_pokemon_detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.pokemonapp.core.data.utils.Result
import com.test.pokemonapp.feature_pokemon_detail.data.models.Pokemon
import com.test.pokemonapp.feature_pokemon_detail.domain.repository.PokemonDetailRepository
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonDetailRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow<Pokemon?>(null)
    val state get(): StateFlow<Pokemon?> = _state

    private val _uiEvent = MutableSharedFlow<String>()
    val uiEvent get() = _uiEvent.asSharedFlow()

    init {
        savedStateHandle.get<String>("name")?.let { getPokemonDetail(it) }
    }

    private fun getPokemonDetail(name: String) {
        repository.getPokemonDetail(name)
            .onEach { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { result.data?.copy(
                            nickname = savedStateHandle.get<String>("nickname")
                        ) }
                    }
                    is Result.Error -> {}
                    is Result.Loading -> {}
                }
            }
            .launchIn(viewModelScope + Dispatchers.IO)
    }

    fun catchPokemon() {
        viewModelScope.launch {
            state.value?.let { pokemon ->
                val chance = Random.nextBoolean()
                if (chance) {
                    _uiEvent.emit("Caught ${pokemon.name}")
                } else {
                    _uiEvent.emit("${pokemon.name} fled")
                }
            }
        }
    }

    fun saveToPokedex(nickname: String? = null) {
        val pokemon = PokemonItem(
            name = savedStateHandle.get<String>("name") ?: "",
            sprite = savedStateHandle.get<String>("sprite") ?: "",
            nickname = if (nickname?.trim().isNullOrEmpty()) null else nickname
        )
        viewModelScope.launch {
            repository.insertCaughtPokemonToPokedex(pokemon)
        }
    }

    fun releasePokemon() {
        viewModelScope.launch {
            state.value?.name?.let { name ->
                repository.deleteCaughtPokemon(name)
            }
        }
    }
}