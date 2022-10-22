package com.test.pokemonapp.core.data.remote

import com.test.pokemonapp.core.data.dto.PokemonDto
import com.test.pokemonapp.core.data.dto.PokemonListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface PokemonService {

    @GET("/api/v2/pokemon")
    suspend fun getPokemonList(): Response<PokemonListDto>

    @GET
    suspend fun loadMorePokemonList(
        @Url url: String
    ): Response<PokemonListDto>

    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemonDetails(
        @Path("name") name: String
    ): Response<PokemonDto>

    companion object {
        const val BASE_URL = "https://pokeapi.co"
    }
}