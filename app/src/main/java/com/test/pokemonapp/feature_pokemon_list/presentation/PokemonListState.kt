package com.test.pokemonapp.feature_pokemon_list.presentation

import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem

data class PokemonListState(
    val nextPageUrl: String? = null,
    val isLoading: Boolean = false,
    val pokemons: List<PokemonItem> = emptyList()
)
