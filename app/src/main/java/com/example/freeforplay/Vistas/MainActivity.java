package com.example.freeforplay.Vistas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.Controladores.HttpConnectGames;
import com.example.freeforplay.R;

public class MainActivity extends AppCompatActivity {

    Button bAcceder;
    Button bToCreateUser;
    Button bToOptions;

    TextView txtUser;
    TextView txtPass;

    DBAccess dba;

    ProgressBar visualCargando;

    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bAcceder = (Button) findViewById(R.id.bAcceder);
        bToCreateUser = (Button) findViewById(R.id.bToCreateNewUser);
        bToOptions = (Button) findViewById(R.id.bToOptions);

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);

        dba = new DBAccess(this);

        visualCargando = (ProgressBar) findViewById(R.id.visualCargandoMain);

        //Pasa a la pantalla de menu
        bAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = txtUser.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                if(name.length() <= 0){
                    toastInfo("Campo de usuario vacio");
                }else{
                    if(pass.length() <= 0){
                        toastInfo("Campo de contraseña vacio");
                    }else{
                        if(dba.checkPassword(name, pass)){
                            bAcceder.setClickable(false);
                            visualCargando.setVisibility(View.VISIBLE);
                            new taskConnections().execute("GET", "/games");
                        }else{
                            toastInfo("Nombre de usuario o contraseña incorrectos");
                        }
                    }
                }
            }
        });

        //Va a la pantalla de Crear usuario
        bToCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, crearUsuario.class);
                startActivity(intent);
            }
        });

        //Va a la pantalla de opciones
        bToOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toastInfo("En construccion");

            }
        });
    }

    private class taskConnections extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String result = null;
            switch (strings[0]){
                case "GET":
                    result = HttpConnectGames.getRequest(strings[1]);
                    break;
                case "POST":
                    result = Integer.toString(HttpConnectGames.postRequest(strings[1],strings[2]));
                    break;
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            if(s != null){
                data = "{\"result\":" + s + "}";

                Intent i = new Intent(MainActivity.this, Menu.class);
                //Probar String builder
                i.putExtra("data", data);

                bAcceder.setClickable(true);

                visualCargando.setVisibility(View.INVISIBLE);

                startActivity(i);

            }else{
                Toast.makeText(MainActivity.this, "Problema al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toastInfo(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}