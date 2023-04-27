package com.example.pokedex.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pokedex.PokeInfo;
import com.example.pokedex.R;
import com.example.pokedex.RecyclerItemClickListener;
import com.example.pokedex.models.Pokemon;

import java.util.ArrayList;

public class ListPokemonAdapter extends RecyclerView.Adapter<ListPokemonAdapter.ViewHolder> {

    public ArrayList<Pokemon> getDataset() {
        return dataset;
    }

    private ArrayList<Pokemon> dataset;
    private Context context;
    private Button poketextView;

    public ListPokemonAdapter(Context context){
        this.context = context;
        dataset = new ArrayList<>();
    }

    //Inflando os dados para aparecer na tela
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);


        return new ViewHolder(view);


    }

    //metodo que consome a API e faz a listagem dos pokemons
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Pokemon p = dataset.get(position);
        viewHolder.poketextView.setText(p.getName());
        viewHolder.poketextView.setText(p.getName());
        //Glide essa blibioteca lida com as imagens (ATENÇAO ENORME EM MEXER C/ ELA PRECISA IMPORTAR NO GRADO, O SDK TEM QUE TA FUNCIONAL, ELE VAI DER ERRO MESMO ASSIM VC VAI PRECISAR IR EM GRADLE.PROPERTIES E HABILITAR: useAndroidX E enableJetifier
        //Essa parte lida diretamente com as imagens
        //Nao tem nenhum tutorial explicando lembresse disso!
        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + p.getNumber() + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.pokeimageView);




       // View poketextView = viewHolder.poketextView.findViewById(R.id.poketextView);
        /*
        viewHolder.poketextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Id do Pokemon " + view, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(view.getContext(), PokeInfo.class);
                Bundle bundle = new Bundle();
                bundle.putString("idPokeInfo", Integer.toString(view.findViewById(R.id.poketextView).getId()));
                intent.putExtras(bundle);
                context.startActivity(intent);

            }

        }
        );

*/
    }
    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addListPokemon(ArrayList<Pokemon> listPokemon) {
        dataset.addAll(listPokemon);
        notifyDataSetChanged();
    }
    //Esse metodo interage diretamente com a RecycleView

    public class ViewHolder extends RecyclerView.ViewHolder{
        //Cria uma variavel paga guardar a imagem e o nome do pokemon
        private ImageView pokeimageView;
        private TextView poketextView;

        //Esse metodo referencia os objetos das Activity e atribui os valores das variaveis a ele
        public ViewHolder(View itemView){
            super(itemView);

            pokeimageView = (ImageView) itemView.findViewById(R.id.pokeimageView);
            poketextView = (TextView) itemView.findViewById(R.id.poketextView);


        }
    }

}
