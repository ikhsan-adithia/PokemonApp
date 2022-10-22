package com.test.pokemonapp.feature_pokemon_list.data.models

data class PokemonList(
    val prev: String?,
    val next: String?,
    val pokemons: List<PokemonItem>
)
