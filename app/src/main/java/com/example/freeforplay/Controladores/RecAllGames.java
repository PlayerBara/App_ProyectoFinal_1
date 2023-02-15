package com.example.freeforplay.Controladores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import com.bumptech.glide.Glide;
import com.example.freeforplay.Modelos.Videojuego;
import com.example.freeforplay.R;
import com.example.freeforplay.Vistas.MoreInfo;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.List;

public class RecAllGames extends RecyclerView.Adapter<RecAllGames.RecyclerHolder>{
    //La lista se utilizara como auxiliar
    List<Videojuego> listGames;
    Context context;
    private CircularProgressDrawable progressDrawable;
    DBAccess dba;

    public RecAllGames(List<Videojuego> listGames) {
        this.listGames = listGames;
    }

    @NonNull
    @Override
    public RecAllGames.RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_game,parent, false);
        RecyclerHolder recyclerHolder = new RecyclerHolder(view);
        context = parent.getContext();
        dba = new DBAccess(context);
        return recyclerHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecAllGames.RecyclerHolder holder, int posicion) {
        Videojuego game = listGames.get(posicion);

        //Se indica como se introduciria los datos en cada elemento de la lista
        holder.txtTitulo.setText(game.getTitle());
        holder.txtGenero.setText(game.getGenero());
        holder.txtPlatform.setText(game.getPlataforma());
        holder.txtPubli.setText(game.getPublisher());

        if(dba.checkGame(game.getId())){
            holder.bAddFav.setText("Borrar de favoritos");
        }

        //Este codigo se utiliza para indicar que se esta cargando las imagenes
        progressDrawable = new CircularProgressDrawable(this.context);
        progressDrawable.setStrokeWidth(10f);
        progressDrawable.setStyle(CircularProgressDrawable.LARGE);
        progressDrawable.setCenterRadius(30f);
        progressDrawable.start();

        //Se utiliza para cargar las imagenes de internet
        Glide.with(holder.imgGame)
                .load(game.getThumbnail())
                .error(R.mipmap.ic_launcher)
                .into(holder.imgGame);

        holder.bAddFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dba.checkGame(game.getId())){
                    createAlertDialog("Borrar de favoritos", "¿Desea borrar de favoritos el juego " + game.getTitle() + " de la lista?", game);
                    if(delFav(game)){
                        holder.bAddFav.setText("Añadir a favoritos");
                    }
                }else{
                    if(addFav(game)){
                        holder.bAddFav.setText("Borrar de favoritos");
                    }
                }
            }
        });

        holder.bMoreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), MoreInfo.class);
                i.putExtra("titulo", game.getTitle());
                i.putExtra("descripcion", game.getDescripcion());
                i.putExtra("genero", game.getGenero());
                i.putExtra("plataforma", game.getPlataforma());
                i.putExtra("publisher", game.getPublisher());
                i.putExtra("desarrolladora", game.getDesarrollador());
                i.putExtra("fechaSalida", game.getFechaSalida());
                i.putExtra("imagen", game.getThumbnail());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listGames.size();
    }

    private boolean addFav(Videojuego game){
        boolean agregado = false;

        String id = game.getId();
        String titulo = game.getTitle();
        String img = game.getThumbnail();
        String desc = game.getDescripcion();
        String genero = game.getGenero();
        String plataforma = game.getPlataforma();
        String publi = game.getPublisher();
        String desarrollador = game.getDesarrollador();
        String fecha = game.getFechaSalida();

        if(dba.insertOnGames(id, titulo, img, desc, genero, plataforma, publi, desarrollador, fecha) != -1){
            Toast.makeText(context, "Añadido a favoritos", Toast.LENGTH_SHORT).show();
            agregado = true;
        }else{
            Toast.makeText(context, "No se pudo añadir a favoritos", Toast.LENGTH_SHORT).show();
        }

        return agregado;
    }

    private boolean delFav(Videojuego game){
        boolean eliminado = false;

        String id = game.getId();

        if(dba.deleteOnGames(id)){
            Toast.makeText(context, "Eliminado de favoritos", Toast.LENGTH_SHORT).show();
            eliminado = true;
        }else{
            Toast.makeText(context, "No se pudo eliminar de favoritos", Toast.LENGTH_SHORT).show();
        }

        return eliminado;
    }

    public AlertDialog createAlertDialog(String titulo, String mensaje, Videojuego game){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(titulo).setMessage(mensaje);

        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(delFav(game)){
                    Toast.makeText(context, "Se borró de favoritos", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "No se borró de favoritos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }

    public class RecyclerHolder extends RecyclerView.ViewHolder {

        TextView txtTitulo;
        TextView txtGenero;
        TextView txtPlatform;
        TextView txtPubli;

        ImageView imgGame;

        Button bMoreInfo;
        Button bAddFav;

        public RecyclerHolder(@NonNull View itemView) {
            super(itemView);

            //Se enlaza los elementos del layout
            txtTitulo  = (TextView) itemView.findViewById(R.id.txtTitulo);
            txtGenero = (TextView) itemView.findViewById(R.id.txtGenero);
            txtPlatform = (TextView) itemView.findViewById(R.id.txtPlatform);
            txtPubli = (TextView) itemView.findViewById(R.id.txtPubli);
            imgGame = (ImageView) itemView.findViewById(R.id.imgGame);
            bMoreInfo = (Button) itemView.findViewById(R.id.bMoreInfo);
            bAddFav = (Button) itemView.findViewById(R.id.bAddFav);
        }
    }
}
