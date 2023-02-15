package com.example.freeforplay.Vistas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.R;

public class crearUsuario extends AppCompatActivity {

    Button bCreateUser;
    Button bCancel;

    TextView txtNewUser;
    TextView txtNewPass;

    DBAccess dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        bCreateUser = (Button) findViewById(R.id.bCreateUser);
        bCancel = (Button) findViewById(R.id.bCancelNewUser);

        txtNewUser = (TextView) findViewById(R.id.txtNewUser);
        txtNewPass = (TextView) findViewById(R.id.txtNewPass);

        dba = new DBAccess(this);

        bCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtNewUser.getText().toString().trim();
                String pass = txtNewPass.getText().toString().trim();

                //Se comprueba de que hay texto en ellos
                if(name.length() >= 4){
                    if(pass.length() < 4){
                        Toast.makeText(crearUsuario.this, "La contraseña debe tener al menos 4 letras", Toast.LENGTH_SHORT).show();
                    }else{
                        //Se checkea si existe el nombre de usuario
                        if(!dba.checkUser(name)){
                            //Se inserta el usuario
                            if(dba.insertUser(name, pass) != -1){
                                Toast.makeText(crearUsuario.this, "Se creo el usuario", Toast.LENGTH_SHORT).show();
                                salir();
                            }else{
                                Toast.makeText(crearUsuario.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(crearUsuario.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(crearUsuario.this, "El usuario debe tener más de 4 letras", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salir();
            }
        });
    }

    private void salir(){
        crearUsuario.this.finish();
    }
}
