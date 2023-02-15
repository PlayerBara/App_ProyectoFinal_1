package com.example.freeforplay.Vistas;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freeforplay.Controladores.HttpConnectGames;
import com.example.freeforplay.Controladores.RecAllGames;
import com.example.freeforplay.Modelos.Videojuego;
import com.example.freeforplay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaGames extends AppCompatActivity {

    RecyclerView recyclerView;
    RecAllGames recAdapter;

    String data = "";

    ArrayList<Videojuego> listGames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_games);

        Bundle extras = getIntent().getExtras();
        data = extras.getString("data");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerGames);

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
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray jsonArray = jsonObject.getJSONArray("result");

            String id = "";
            String nom = "";
            String img = "";
            String desc = "";
            String genero = "";
            String plataforma = "";
            String publisher = "";
            String desarrollador = "";
            String fechaSalida = "";

            for (int i = 0; i < jsonArray.length(); i++){
                id = jsonArray.getJSONObject(i).getString("id");
                nom = jsonArray.getJSONObject(i).getString("title");
                img = jsonArray.getJSONObject(i).getString("thumbnail");
                desc = jsonArray.getJSONObject(i).getString("short_description");
                genero = jsonArray.getJSONObject(i).getString("genre");
                plataforma = jsonArray.getJSONObject(i).getString("platform");
                publisher = jsonArray.getJSONObject(i).getString("publisher");
                desarrollador = jsonArray.getJSONObject(i).getString("developer");
                fechaSalida = jsonArray.getJSONObject(i).getString("release_date");

                listGames.add(new Videojuego(id, nom, img, desc, genero, plataforma, publisher, desarrollador, fechaSalida));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
