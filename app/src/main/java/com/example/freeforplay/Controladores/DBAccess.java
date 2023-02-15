package com.example.freeforplay.Controladores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.freeforplay.Modelos.Videojuego;

import java.util.ArrayList;

public class DBAccess extends SQLiteOpenHelper {

    //Nombre de la base de datos
    private static final String DB_NAME = "FreeGamesApp";
    //Version de la base de datos
    private static final int DB_VERSION = 1;

    //Nombre de la tabla de la base de datos
    private static final String DB_TABLE_GAMES = "Juegos";
    //Columnas de la tabla
    private static final String ID_COLUMN = "ID";
    private static final String TITULO_COLUMN = "Titulo";
    private static final String IMG_COLUMN = "Imagen";
    private static final String DESCRIPCION_COLUMN = "Descripcion";
    private static final String GENERO_COLUMN = "Genero";
    private static final String PLATAFORMA_COLUMN = "Plataforma";
    private static final String PUBLISHER_COLUMN = "Publisher";
    private static final String DESARROLLADORA_COLUMN = "Desarolladora";
    private static final String FECHASALIDA_COLUMN = "Fecha_Salida";

    //Nombre de la tabla de la base de datos
    private static final String DB_TABLE_USER = "Usuarios";
    //Columnas de la tabla
    private static final String USER_COLUMN = "Nombre";
    private static final String PASSWORD_COLUMN = "Password";

    //Contexto de la aplicación
    private Context mContext;

    public DBAccess(Context context){
        super(context, DB_NAME, null, DB_VERSION);

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Se los comandos para crear una tabla en sqlite
        String CREATE_GAMES_TABLE = "CREATE TABLE " + DB_TABLE_GAMES + "("
                + ID_COLUMN + " TEXT PRIMARY KEY, "
                + TITULO_COLUMN + " TEXT NOT NULL, "
                + IMG_COLUMN + " TEXT NOT NULL, "
                + DESCRIPCION_COLUMN + " TEXT NOT NULL, "
                + GENERO_COLUMN + " TEXT NOT NULL, "
                + PLATAFORMA_COLUMN + " TEXT NOT NULL, "
                + PUBLISHER_COLUMN + " TEXT NOT NULL, "
                + DESARROLLADORA_COLUMN + " TEXT NOT NULL, "
                + FECHASALIDA_COLUMN + " TEXT NOT NULL)";
        //Se ejecuta el comando antes realizado en CREATE_GAMES_TABLE
        sqLiteDatabase.execSQL(CREATE_GAMES_TABLE);

        //Se los comandos para crear una tabla en sqlite
        String CREATE_USER_TABLE = "CREATE TABLE " + DB_TABLE_USER + "("
                + USER_COLUMN + " TEXT PRIMARY KEY, "
                + PASSWORD_COLUMN + " TEXT NOT NULL)";
        //Se ejecuta el comando antes realizado en CREATE_USER_TABLE
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

    public long insertOnGames(String id, String title, String thumbnail, String descripcion, String genero, String plataforma, String publisher, String desarrollador, String fechaSalida){
        //Iniciamos SQLite y lo hacemos para que podamos escribir y leer
        SQLiteDatabase db = this.getWritableDatabase();
        //Creamos el result -1 para que si no se puede añadir salga un valor negativo
        long result = -1;

        //Creamos un contenedor donde almacenaremos las columnas y sus nuevos valores
        ContentValues values = new ContentValues();

        //Añadimos valores al contenedor
        values.put(ID_COLUMN, id);
        values.put(TITULO_COLUMN, title);
        values.put(IMG_COLUMN, thumbnail);
        values.put(DESCRIPCION_COLUMN, descripcion);
        values.put(GENERO_COLUMN, genero);
        values.put(PLATAFORMA_COLUMN, plataforma);
        values.put(PUBLISHER_COLUMN, publisher);
        values.put(DESARROLLADORA_COLUMN, desarrollador);
        values.put(FECHASALIDA_COLUMN, fechaSalida);

        //Insertamos ese contenedor dentro del log result
        result = db.insert(DB_TABLE_GAMES, null, values);

        //Cerramos la base de datos
        db.close();

        return result;
    }

    public boolean deleteOnGames (String id){
        boolean eliminado = true;
        //Iniciamos SQLite y lo hacemos para que podamos escribir y leer
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE_GAMES, ID_COLUMN + " = '" + id + "'",null);

        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_GAMES +
                " WHERE " + ID_COLUMN + " = '" + id + "'", null);

        if(c.moveToFirst()){
            eliminado = false;
        }

        c.close();
        db.close();

        return eliminado;
    }

    public long insertUser(String nombre, String password){
        //Iniciamos SQLite y lo hacemos para que podamos escribir y leer
        SQLiteDatabase db = this.getWritableDatabase();
        //Creamos el result -1 para que si no se puede añadir salga un valor negativo
        long result = -1;

        //Creamos un contenedor donde almacenaremos las columnas y sus nuevos valores
        ContentValues values = new ContentValues();

        //Añadimos valores al contenedor
        values.put(USER_COLUMN, nombre);
        values.put(PASSWORD_COLUMN, password);

        //Insertamos ese contenedor dentro del log result
        result = db.insert(DB_TABLE_USER, null, values);

        //Cerramos la base de datos
        db.close();

        return result;
    }

    public boolean checkUser (String user){

        boolean existe = false;
        SQLiteDatabase db = this.getReadableDatabase();

        //Creamos un cursor que nos dara si funciona correctamente o 1 o ningun registro de la tabla
        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_USER +
                " WHERE " + USER_COLUMN + " = '" + user + "'", null);

        //Si se mueve al principio entonces significa que existe ese registro
        if(c.moveToFirst()){
            existe = true;
        }

        c.close();
        db.close();
        return existe;
    }

    public boolean checkPassword (String user, String pass){
        boolean existe = false;
        SQLiteDatabase db = this.getReadableDatabase();
        try{
            //Se almacena el registro si existe
            Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_USER + " " +
                    "WHERE " + USER_COLUMN + " = '" + user +
                    "' AND " + PASSWORD_COLUMN + " = '" + pass + "'", null);

            //Se comprueba si existe ese dato
            if(c.moveToFirst()){
                existe = true;
            }

            c.close();
        } catch (SQLException ex){ }

        db.close();
        return existe;
    }

    public boolean checkGame (String id){
        boolean existe = false;
        SQLiteDatabase db = this.getReadableDatabase();

        //Creamos un cursor que nos dara si funciona correctamente o 1 o ningun registro de la tabla
        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_GAMES  +
                " WHERE " + ID_COLUMN + " = '" + id + "'", null);

        //Si se mueve al principio entonces significa que existe ese registro
        if(c.moveToNext()){
            existe = true;
        }

        c.close();
        db.close();
        return existe;
    }

    public int checkCantGames (){
        int cant = 0;

        SQLiteDatabase db = this.getReadableDatabase();

        cant = (int) DatabaseUtils.queryNumEntries(db, DB_TABLE_GAMES);

        db.close();

        return cant;
    }

    public ArrayList<Videojuego> getGames (){
        ArrayList<Videojuego> games = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DB_TABLE_GAMES, null);

        if(c.moveToFirst()){
            do{
                games.add(new Videojuego(c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7),
                        c.getString(8)));
            }while (c.moveToNext());
        }
        c.close();
        db.close();

        return games;
    }

    public void deleteUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + DB_TABLE_USER);
        db.close();
    }
}
