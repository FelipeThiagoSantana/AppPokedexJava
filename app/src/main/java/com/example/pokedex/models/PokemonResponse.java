package com.example.pokedex.models;
//Bom Esse classe manipula os dados de repostas da API vindo da classe Pokemon
import java.util.ArrayList;

public class PokemonResponse {

    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
