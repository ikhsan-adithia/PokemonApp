package com.test.pokemonapp.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.pokemonapp.feature_pokemon_list.data.models.PokemonItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CaughtPokemonDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pokemon: PokemonItem)

    @Query("DELETE FROM PokemonItem WHERE name = :name")
    suspend fun delete(name: String)

    @Query("SELECT * FROM PokemonItem")
    fun getCaughtPokemon(): Flow<List<PokemonItem>>
}