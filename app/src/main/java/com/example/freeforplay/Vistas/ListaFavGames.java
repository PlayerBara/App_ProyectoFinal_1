package com.example.freeforplay.Vistas;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.Controladores.RecAllGames;
import com.example.freeforplay.Modelos.Videojuego;
import com.example.freeforplay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaFavGames extends AppCompatActivity {

    RecyclerView recyclerView;
    RecAllGames recAdapter;

    DBAccess dba;

    ArrayList <Videojuego> listGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_games);

        dba = new DBAccess(this);

        recyclerView = (RecyclerView) findViewById(R.id.recFavGames);

        cargarDatos();

        mostrarDatos();
    }

    private void mostrarDatos(){
        //Se crea el recyclerAdapter y se le añade la lista donde estara almacenado los datos del JSON procesados
        recAdapter = new RecAllGames(listGames);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void cargarDatos(){
        listGames = dba.getGames();
    }
}
