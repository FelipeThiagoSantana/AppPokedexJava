package com.example.pokedex;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.adapter.ListPokemonAdapter;
import com.example.pokedex.models.Pokemon;
import com.example.pokedex.models.PokemonResponse;
import com.example.pokedex.pokeapi.PokeapiService;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private static final String TAG = "POKEDEX";

    private RecyclerView recyclerView;
    private ListPokemonAdapter listPokemonAdapter;
    private  int offset; //Estou usando essa variavel para limitar a busca por pokemons
    private boolean loadPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recyclerView);
        listPokemonAdapter = new ListPokemonAdapter(this);
        recyclerView.setAdapter(listPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        //Esse parametro faz com que liste 3 pokemon por fileira
        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        //Esse parametro faz funcionar o Scroll para carregamento dos pokemons
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                 }



            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //Esse If verifica se o Scrow chegou abixo e se e o ultimo item
                if (dy > 0 ){
                    int visibleItemCount = layoutManager.getItemCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItens = layoutManager.findFirstVisibleItemPosition();

                    if (loadPokemon){
                        if ((visibleItemCount + pastVisibleItens) >= totalItemCount){
                            Log.i(TAG, "Fim");
                            loadPokemon = false;
                            offset += 20;
                            getData(offset);


                        }
                    }
                }
            }
        });

//Retrofit aqui a base da url da api
        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        offset = 0;

        getData(offset);

    }

// esse metodo pegar os dados da API
    private void getData(int offset){
       PokeapiService service = retrofit.create(PokeapiService.class);
       Call<PokemonResponse> pokemonResponseCall =  service.getListPokemon(20,offset); //Esse offest configura o numero limite de pokemon

       pokemonResponseCall.enqueue(new Callback<PokemonResponse>() {
           @Override
           public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {

               loadPokemon = true; //Instanciando a variavel para garantir o carregamento

               if (response.isSuccessful()){

                   PokemonResponse pokemonResponse = response.body();
                   ArrayList<Pokemon> listPokemon = pokemonResponse.getResults();

                   listPokemonAdapter.addListPokemon(listPokemon);

               }
               else{
                   Log.e(TAG, "onResponse: " + response.errorBody());
               }
           }

           @Override
           public void onFailure(Call<PokemonResponse> call, Throwable t) {
               loadPokemon = true;
               Log.e(TAG, "onFailure: " + t.getMessage());

           }
       });

    }
}