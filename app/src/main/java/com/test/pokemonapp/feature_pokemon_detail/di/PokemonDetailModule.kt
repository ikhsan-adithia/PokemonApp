package com.test.pokemonapp.feature_pokemon_detail.di

import com.test.pokemonapp.core.data.local.CaughtPokemonDatabase
import com.test.pokemonapp.core.data.remote.PokemonService
import com.test.pokemonapp.feature_pokemon_detail.data.repository.PokemonDetailRepositoryImpl
import com.test.pokemonapp.feature_pokemon_detail.domain.repository.PokemonDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonDetailModule {

    @Provides
    @Singleton
    fun providePokemonDetailRepository(service: PokemonService, db: CaughtPokemonDatabase): PokemonDetailRepository =
        PokemonDetailRepositoryImpl(service, db.dao)
}