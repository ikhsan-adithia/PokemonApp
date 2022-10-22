package com.test.pokemonapp.feature_pokemon_list.di

import com.test.pokemonapp.core.data.remote.PokemonService
import com.test.pokemonapp.feature_pokemon_list.data.repository.PokemonListRepositoryImpl
import com.test.pokemonapp.feature_pokemon_list.domain.repository.PokemonListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonListModule {

    @Provides
    @Singleton
    fun providesPokemonListRepository(service: PokemonService): PokemonListRepository =
        PokemonListRepositoryImpl(service)
}