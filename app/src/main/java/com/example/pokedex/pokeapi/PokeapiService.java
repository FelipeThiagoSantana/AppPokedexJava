package com.example.pokedex.pokeapi;

import com.example.pokedex.models.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
//Interface magica ela faz listagem dos pokemons
public interface PokeapiService {

    @GET("pokemon")
    Call<PokemonResponse>getListPokemon(@Query("limit")int limit, @Query("offset") int offset);
}
