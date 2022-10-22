package com.test.pokemonapp.core.di

import android.app.Application
import androidx.room.Room
import com.test.pokemonapp.core.data.local.CaughtPokemonDatabase
import com.test.pokemonapp.core.data.remote.PokemonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PokemonAppModule {

    @Provides
    @Singleton
    fun providePokemonService(): PokemonService =
        Retrofit.Builder()
            .baseUrl(PokemonService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)

    @Provides
    @Singleton
    fun provideCaughtPokemonDatabase(application: Application) = Room.databaseBuilder(
        application,
        CaughtPokemonDatabase::class.java,
        CaughtPokemonDatabase.DATABASE_NAME
    ).build()
}