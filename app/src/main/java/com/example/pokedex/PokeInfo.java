package com.example.pokedex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class PokeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_info);


       Integer id = Integer.parseInt(getIntent().getExtras().getString("idPokeInfo"));
        Log.d("=>>>>>>>", id.toString());
        Toast.makeText(this,id, Toast.LENGTH_LONG).show();
    }
}