package com.test.pokemonapp.core.data.local

import androidx.annotation.Keep
import androidx.room.Database
import androidx.room.RoomDatabase
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem

@Database(
    entities = [PokemonItem::class],
    version = CaughtPokemonDatabase.versionDB,
    exportSchema = false
)
abstract class CaughtPokemonDatabase: RoomDatabase() {

    abstract val dao: CaughtPokemonDao

    @Keep
    companion object {
        const val versionDB = 1
        const val DATABASE_NAME = "CaughtPokemonDatabase"
    }
}