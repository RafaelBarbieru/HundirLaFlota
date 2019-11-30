package com.example.hundirlaflota.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hundirlaflota.config.DatabaseConfig;

public class Database {

    private SQLiteDatabase db;
    private Context context;

    public Database(Context context) {
        this.context = context;
    }

    /**
     * Método que crea o se conecta a la base de datos
     */
    public void conectarCrearBaseDatos() {
        // Crear o conectarse a una base de datos existente
        db = context.openOrCreateDatabase(DatabaseConfig.NOMBRE_BASE_DATOS, context.MODE_PRIVATE, null);
    }

    /**
     * Método que crea la tabla de puntuaciones si esta no existe
     */
    public void crearTabla() {
        // Creamos la base de datos si no existe
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatabaseConfig.NOMBRE_TABLA_PUNTUACIONES + "(nombreJugador VARCHAR, puntuacion NUMBER)");
    }

    /**
     * Método que inserta datos en la base de datos
     */
    public void insertarDatos(String nombreJugador, int puntuacion) {
        // Lanzamos la consulta de insertado
        db.execSQL("INSERT INTO " + DatabaseConfig.NOMBRE_TABLA_PUNTUACIONES + " (nombreJugador, puntuacion) VALUES (\'" + nombreJugador +"\'," + puntuacion + ") ");
    }

    /**
     * Método que cierra la conexión con la base de datos
     */
    public void cerrar() {
        db.close();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

}
