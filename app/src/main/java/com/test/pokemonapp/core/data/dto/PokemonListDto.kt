package com.test.pokemonapp.core.data.dto

import com.google.gson.annotations.SerializedName
import com.test.pokemonapp.core.data.remote.PokemonService
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonList

data class PokemonListDto(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("results")
	val results: List<PokemonResult>
) {
	fun toPokemonList(): PokemonList {
		val data = PokemonList(
			prev = previous,
			next = next,
			pokemons = results.map { pokemon ->
				val id = pokemon.url.removePrefix("${PokemonService.BASE_URL}/api/v2/pokemon/").removeSuffix("/")
				PokemonItem(
					name = pokemon.name,
					sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
				)
			}
		)

		return data
	}
}

data class PokemonResult(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("url")
	val url: String
)
