package com.example.freeforplay.Vistas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.Controladores.HttpConnectGames;
import com.example.freeforplay.Controladores.SettingActivity;
import com.example.freeforplay.R;

public class MainActivity extends AppCompatActivity {

    //Se crean las propiedades necesarias
    Button bAcceder;
    Button bToCreateUser;
    Button bToOptions;

    TextView txtUser;
    TextView txtPass;

    //Se crea la base de datos
    DBAccess dba;

    ProgressBar visualCargando;

    //Este String se utilizara para almacenar el JSON de la api
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se enlaza los elementos de la pantalla con los creados en esta clase
        bAcceder = (Button) findViewById(R.id.bAcceder);
        bToCreateUser = (Button) findViewById(R.id.bToCreateNewUser);
        bToOptions = (Button) findViewById(R.id.bToOptions);

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtPass = (TextView) findViewById(R.id.txtPass);

        //Se inicializa la base de datos
        dba = new DBAccess(this);

        //Se cargan las preferencias que se habian guardado
        loadPreferences();

        //Se inicializa el simbolo de cargando
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
                            //Inhabilita el boton para que el usuario no pueda crear muchas pantallas antes de que carguen los datos
                            bAcceder.setClickable(false);
                            //Hace visible el progressBar
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
                Intent i = new Intent(MainActivity.this, SettingActivity.class);

                startActivity(i);

            }
        });
    }

    //Se cargan las preferencias
    private void loadPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String userText = sharedPreferences.getString("emailPreference","");
        String passText = sharedPreferences.getString("passwordPreference","");

        txtUser.setText(userText);
        txtPass.setText(passText);
    }

    //Se conecta con la api
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
                //Se le añade el código necesario y se le adjunta el JSON
                data = "{\"result\":" + s + "}";

                Intent i = new Intent(MainActivity.this, Menu.class);
                //Se pasa los datos del JSON para las siguientes pantallas, asi no hace falta obtenerlos de internet de nuevo
                i.putExtra("data", data);

                //Hace que el boton sea accesible otra vez
                bAcceder.setClickable(true);

                //Se guarda el simbolo de cargando
                visualCargando.setVisibility(View.INVISIBLE);

                //Se inicializa la pantalla una vez terminada la carga de datos
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