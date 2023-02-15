package com.example.freeforplay.Vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.Modelos.Videojuego;
import com.example.freeforplay.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RandomGame extends AppCompatActivity {

    ImageView imagen;
    TextView titulo;
    TextView descripcion;
    TextView genero;
    TextView plataforma;
    TextView publi;
    TextView desarrolladora;
    TextView fechaSalida;

    Button bNewRandomGame;
    Button bAddFav;

    DBAccess dba;

    String data = "";

    int randomGame;

    ArrayList<Videojuego> listaGames= new ArrayList <Videojuego> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_game);

        imagen = (ImageView) findViewById(R.id.imgRandom);
        titulo = (TextView) findViewById(R.id.txtTituloRandom);
        descripcion = (TextView) findViewById(R.id.txtDescRandom);
        genero = (TextView) findViewById(R.id.txtGeneroRandom);
        plataforma = (TextView) findViewById(R.id.txtPlataformaRandom);
        publi = (TextView) findViewById(R.id.txtPubliRandom);
        desarrolladora = (TextView) findViewById(R.id.txtDesarrolladoraRandom);
        fechaSalida = (TextView) findViewById(R.id.txtFechaSalidaRandom);

        bNewRandomGame = (Button) findViewById(R.id.bNewRandom);
        bAddFav = (Button) findViewById(R.id.bAddFavRandom);

        dba = new DBAccess(this);

        cargarDatos();

        newRandomGame();

        bNewRandomGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newRandomGame();
            }
        });

        bAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = listaGames.get(randomGame).getId();

                if(!dba.checkGame(listaGames.get(randomGame).getId())){
                    String nom = listaGames.get(randomGame).getTitle();
                    String img = listaGames.get(randomGame).getThumbnail();
                    String desc = listaGames.get(randomGame).getDescripcion();
                    String genero = listaGames.get(randomGame).getGenero();
                    String plataforma = listaGames.get(randomGame).getPlataforma();
                    String publisher = listaGames.get(randomGame).getPublisher();
                    String desarrollador = listaGames.get(randomGame).getDesarrollador();
                    String fechaSalida = listaGames.get(randomGame).getFechaSalida();

                    if(dba.insertOnGames(id, nom, img, desc, genero, plataforma, publisher, desarrollador, fechaSalida) != -1){
                        Toast.makeText(RandomGame.this, "Se añadio a favoritos", Toast.LENGTH_SHORT).show();
                        bAddFav.setText("Borrar de favoritos");
                    }else{
                        Toast.makeText(RandomGame.this, "No se pudo añadir a favoritos", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(dba.deleteOnGames(id)){
                        Toast.makeText(RandomGame.this, "Se borro correctamente el juego de favoritos", Toast.LENGTH_SHORT).show();
                        bAddFav.setText("Añadir a favoritos");
                    }else{
                        Toast.makeText(RandomGame.this, "No se pudo eliminar de favoritos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void cargarDatos(){
        Bundle extras = getIntent().getExtras();
        data = extras.getString("data");

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

                listaGames.add(new Videojuego(id, nom, img, desc, genero, plataforma, publisher, desarrollador, fechaSalida));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void newRandomGame(){
        int max = listaGames.size() - 1;

        randomGame = (int)(Math.random()*(max-0+1)+0);

        Videojuego game = listaGames.get(randomGame);

        mostrarDatos(game);
    }

    private void mostrarDatos(Videojuego game){
        titulo.setText(game.getTitle());
        descripcion.setText(game.getDescripcion());
        genero.setText("Genero: " + game.getGenero());
        plataforma.setText("Plataforma: " + game.getPlataforma());
        publi.setText("Publisher: " + game.getPublisher());
        desarrolladora.setText("Desarrolladora: " + game.getDesarrollador());
        fechaSalida.setText("Fecha de salida: " + game.getFechaSalida());

        checkearJuegoFav(game.getId());

        Glide.with(imagen)
                .load(game.getThumbnail())
                .error(R.mipmap.ic_launcher)
                .into(imagen);
    }

    private void checkearJuegoFav (String id){
        if (dba.checkGame(id)){
            bAddFav.setText("Borrar de favoritos");
        }else{
            bAddFav.setText("Añadir a favoritos");
        }
    }
}
