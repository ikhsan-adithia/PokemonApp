package com.test.pokemonapp.core.data.dto

import com.google.gson.annotations.SerializedName
import com.test.pokemonapp.core.data.utils.getTypeColor
import com.test.pokemonapp.feature_pokemon_detail.data.models.Pokemon
import com.test.pokemonapp.feature_pokemon_detail.domain.models.Stat
import com.test.pokemonapp.feature_pokemon_detail.domain.models.Type
import kotlin.math.max

data class PokemonDto(
    val height: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val sprites: Sprites,
    val stats: List<StatsDto>,
    val types: List<TypeDto>,
    val weight: Int
) {
    fun toPokemon() = Pokemon(
        name = name,
        sprite = sprites.frontDefault,
        types = types.map {
            Type(
                name = it.typeValue.name,
                color = it.typeValue.name.getTypeColor()
            )
        },
        stats = stats.map { dto ->
            val max = stats.map { it.baseStat }.maxByOrNull { it }
            Stat(
                name = dto.stat.name,
                maxProgress = max ?: 0,
                currentProgress = if(max == null) 0 else dto.baseStat
            )
        }
    )
}

data class Sprites(
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
)

data class StatsDto(
    @SerializedName("base_stat")
    val baseStat: Int,
    @SerializedName("stat")
    val stat: StatX
)

data class StatX(
    val name: String
)

data class TypeDto(
    @SerializedName("type")
    val typeValue: TypeValue
)

data class TypeValue(
    val name: String
)