package com.test.pokemonapp.feature_pokedex.di

import com.test.pokemonapp.core.data.local.CaughtPokemonDatabase
import com.test.pokemonapp.feature_pokedex.data.repository.PokedexRepositoryImpl
import com.test.pokemonapp.feature_pokedex.domain.repository.PokedexRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokedexModule {

    @Provides
    @Singleton
    fun providePokedexRepository(db: CaughtPokemonDatabase): PokedexRepository = PokedexRepositoryImpl(db.dao)
}