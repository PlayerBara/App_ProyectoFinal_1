package com.example.freeforplay.Vistas;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dd.morphingbutton.MorphingButton;
import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.R;

public class crearUsuario extends AppCompatActivity {

    //Usado a partir de una biblioteca externa
    MorphingButton bCreateUser;

    //crea los atributos de la pantalla
    Button bCancel;

    TextView txtNewUser;
    TextView txtNewPass;

    //Crea la base de datos
    DBAccess dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user);

        //Crea el boton de la libreria externa que elegi
        bCreateUser = (MorphingButton) findViewById(R.id.bCreateUser);

        //Se crean los estados del boton
        MorphingButton.Params normal = MorphingButton.Params.create()
                .duration(5)
                .text("Crear usuario")
                .width(500)
                .height(150)
                .cornerRadius(100)
                .color(Color.rgb(255, 152, 0))
                .colorPressed(Color.GRAY);

        MorphingButton.Params error = MorphingButton.Params.create()
                .duration(500)
                .text("Crear usuario")
                .width(500)
                .height(150)
                .cornerRadius(100)
                .color(Color.RED)
                .colorPressed(Color.GRAY);

        //Se pone el boton del estado normal
        bCreateUser.morph(normal);

        //Se enlaza los atributos de la pantalla con los del activity
        bCancel = (Button) findViewById(R.id.bCancelNewUser);

        txtNewUser = (TextView) findViewById(R.id.txtNewUser);
        txtNewPass = (TextView) findViewById(R.id.txtNewPass);

        //Se inicializa la base de datos
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
                                bCreateUser.morph(error);

                                Toast.makeText(crearUsuario.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            bCreateUser.morph(error);

                            Toast.makeText(crearUsuario.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    bCreateUser.morph(error);

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
