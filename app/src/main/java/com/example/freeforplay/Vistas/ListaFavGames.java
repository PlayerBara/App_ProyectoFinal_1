package com.example.freeforplay.Vistas;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freeforplay.Controladores.DBAccess;
import com.example.freeforplay.Controladores.RecAllGames;
import com.example.freeforplay.Modelos.Videojuego;
import com.example.freeforplay.R;

import java.util.ArrayList;

public class ListaFavGames extends AppCompatActivity {

    //Se crea los recyclers
    RecyclerView recyclerView;
    RecAllGames recAdapter;

    //Se crea la base de datos
    DBAccess dba;

    //Se crea la lista para los recyclers
    ArrayList <Videojuego> listGames = new ArrayList<>();

    //se crea el action Menu
    private ActionMode actionMode;
    private int posicionJuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_games);

        //Se inicaliza la base de datos
        dba = new DBAccess(this);

        //Se incializa el recycler
        recyclerView = (RecyclerView) findViewById(R.id.recFavGames);

        //Se carga y muestra los datos
        cargarDatos();

        mostrarDatos();

        //Si se mantiene pulsado te da la opción de borrar el objeto
        recAdapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                boolean resultado = false;
                posicionJuego = recyclerView.getChildAdapterPosition(view);

                if(actionMode == null){
                    actionMode = startSupportActionMode(actionMenu);
                    resultado = true;
                }

                return resultado;
            }
        });
    }

    //Muestra los datos
    private void mostrarDatos(){
        //Se crea el recyclerAdapter y se le añade la lista donde estara almacenado los datos del JSON procesados
        recAdapter = new RecAllGames(listGames);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setAdapter(recAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    //Carga los daros
    private void cargarDatos(){
        listGames = dba.getGames();
    }

    //Crea el Action menu
    private ActionMode.Callback actionMenu = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_menu,menu);
            mode.setTitle("Action Menu");
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();

            switch(itemId){
                case R.id.act_delete:
                    //Se crea el alert dialog que dara la opcion de borrar el elemento
                    AlertDialog alertDialog = useAlertDialog("¿Desea eliminar el juego?", posicionJuego);
                    alertDialog.show();
                    mode.finish();
                    break;
            }

            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    //Se crea el action menu
    private AlertDialog useAlertDialog(String msg, int pos){

        AlertDialog.Builder builder = new AlertDialog.Builder(ListaFavGames.this);

        builder.setMessage(msg)
                .setTitle("Atención");

        //Si dice que si se borra del recycler, de la lista y de la base de datos
        builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dba.deleteOnGames(listGames.get(pos).getId());
                listGames.remove(pos);
                recAdapter.notifyDataSetChanged();
                Toast.makeText(ListaFavGames.this, "Se elimino el juego de favoritos", Toast.LENGTH_SHORT).show();
            }
        });

        //Si dice que no no hace nada
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListaFavGames.this, "Se cancelo el borrado", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
