package com.example.freeforplay.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.R;

public class Menu extends AppCompatActivity {

    Button bListGames;
    Button bRandomGame;
    Button bListFav;
    Button bExit;

    DBAccess dba;

    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //Enlazado de botones
        bListGames = (Button) findViewById(R.id.bListGames);
        bRandomGame = (Button) findViewById(R.id.bRandomGame);
        bListFav = (Button) findViewById(R.id.bListFav);
        bExit = (Button) findViewById(R.id.bExit);

        dba = new DBAccess(this);

        cargarDatos();

        bListGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, ListaGames.class);

                i.putExtra("data", data);

                startActivity(i);
            }
        });

        bRandomGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Menu.this, RandomGame.class);

                i.putExtra("data", data);

                startActivity(i);
            }
        });

        bListFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dba.checkCantGames() > 0){
                    Intent i = new Intent(Menu.this, ListaFavGames.class);

                    startActivity(i);
                }else{
                    Toast.makeText(Menu.this, "No hay juegos añadidos a favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Menu.this.finish();
            }
        });

    }

    private void cargarDatos(){
        Bundle extras = getIntent().getExtras();

        data = extras.getString("data");
    }
}
