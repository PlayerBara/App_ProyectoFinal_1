package com.example.freeforplay.Vistas;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.freeforplay.R;

public class MoreInfo extends AppCompatActivity {

    ImageView imgGame;
    TextView titulo;
    TextView descripcion;
    TextView genero;
    TextView plataforma;
    TextView publisher;
    TextView desarrolladora;
    TextView fechaSalida;

    private CircularProgressDrawable progressDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_extra);

        imgGame = (ImageView) findViewById(R.id.imgGameInfo);
        titulo = (TextView) findViewById(R.id.txtTituloInfo);
        descripcion = (TextView) findViewById(R.id.txtDescInfo);
        genero = (TextView) findViewById(R.id.txtGeneroInfo);
        plataforma = (TextView) findViewById(R.id.txtPlataformaInfo);
        publisher = (TextView) findViewById(R.id.txtPubliInfo);
        desarrolladora = (TextView) findViewById(R.id.txtDesarrolladorInfo);
        fechaSalida = (TextView) findViewById(R.id.txtFechaSalidaInfo);

        Intent i = getIntent();
        titulo.setText(i.getExtras().getString("titulo"));
        descripcion.setText(i.getExtras().getString("descripcion"));
        genero.setText(genero.getText() + i.getExtras().getString("genero"));
        plataforma.setText(plataforma.getText() + i.getExtras().getString("plataforma"));
        publisher.setText(publisher.getText() + i.getExtras().getString("publisher"));
        desarrolladora.setText(desarrolladora.getText() + i.getExtras().getString("desarrolladora"));
        fechaSalida.setText(fechaSalida.getText() + i.getExtras().getString("fechaSalida"));

        //Este codigo se utiliza para indicar que se esta cargando las imagenes
        progressDrawable = new CircularProgressDrawable(this);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        //Se utiliza para cargar las imagenes de internet
        Glide.with(imgGame)
                .load(i.getExtras().getString("imagen"))
                .error(R.mipmap.ic_launcher)
                .into(imgGame);
    }
}
