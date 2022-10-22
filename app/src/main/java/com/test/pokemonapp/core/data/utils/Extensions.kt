package com.test.pokemonapp.core.data.utils

import com.test.pokemonapp.R

fun String.getTypeColor(): Int {
    return when(this.lowercase()) {
        "normal" -> { R.color.color_normal }
        "fire" -> { R.color.color_fire }
        "water" -> { R.color.color_water }
        "grass" -> { R.color.color_grass }
        "electric" -> { R.color.color_electric }
        "ice" -> { R.color.color_ice }
        "fighting" -> { R.color.color_fighting }
        "poison" -> { R.color.color_poison }
        "ground" -> { R.color.color_ground }
        "flying" -> { R.color.color_flying }
        "psychic" -> { R.color.color_psychic }
        "bug" -> { R.color.color_bug }
        "rock" -> { R.color.color_rock }
        "ghost" -> { R.color.color_ghost }
        "dark" -> { R.color.color_dark }
        "dragon" -> { R.color.color_dragon }
        "steel" -> { R.color.color_steel }
        else -> { R.color.color_normal }
    }
}