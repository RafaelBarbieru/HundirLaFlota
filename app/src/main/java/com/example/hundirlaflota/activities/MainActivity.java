package com.example.hundirlaflota.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.DatabaseConfig;
import com.example.hundirlaflota.config.GameConfig;
import com.example.hundirlaflota.dialogs.DifficultyDialog;
import com.example.hundirlaflota.dialogs.InfoDialog;
import com.example.hundirlaflota.dialogs.ThemeDialog;
import com.example.hundirlaflota.utils.Database;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseConfig.database = new Database(this);
        DatabaseConfig.database.conectarCrearBaseDatos();
        DatabaseConfig.database.crearTabla();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    /**
     * Método que crea el menú superior
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    /**
     * Método que controla la acción a ejecutar al pulsar en una de las opciones de la barra superior
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_empezar:
                onEmpezar();
                return true;
            case R.id.item_temas:
                onTemas();
            case R.id.item_puntuaciones:
                onPuntuaciones();
                return true;
            case R.id.item_ayuda:
                onAyuda();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DatabaseConfig.database.cerrar();
    }

    // Métodos de los botones

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Empezar
     * @param v
     */
    public void onButtonEmpezar(View v) {
        onEmpezar();
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Temas
     * @param v
     */
    public void onButtonTemas(View v) {
        onTemas();
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Puntuaciones
     * @param v
     */
    public void onButtonPuntuaciones(View v) {
        onPuntuaciones();
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Ayuda
     * @param v
     */
    public void onButtonAyuda(View v) {
        onAyuda();
    }


    // Métodos funcionales

    /**
     * Método que se ejecuta cuando se quiere acceder a Empezar
     */
    public void onEmpezar() {
        DifficultyDialog difficultyDialog = new DifficultyDialog(this);
        difficultyDialog.show(getSupportFragmentManager(), "difficultyDialog");
    }

    /**
     * Método que se ejecuta cuando se quiere acceder a Temas
     */
    public void onTemas() {
        ThemeDialog themeDialog = new ThemeDialog(this);
        themeDialog.show(getSupportFragmentManager(), "themeDialog");
    }

    /**
     * Método que se ejecuta cuando se quiere acceder a Puntuaciones
     */
    public void onPuntuaciones() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    /**
     * Método que se ejecuta cuando se quiere acceder a Ayuda
     */
    public void onAyuda() {
        InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_MENU);
        infoDialog.show(getSupportFragmentManager(), "infoDialog");
    }



}
