package com.example.hundirlaflota.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.DatabaseConfig;
import com.example.hundirlaflota.dialogs.ConfirmDialog;

public class EndOfGameActivity extends AppCompatActivity {

    // Atributos
    private TextView tv_titulo;
    private TextView tv_puntuacion;
    private EditText tx_nombreJugador;
    private Bundle bundle;
    private int puntuacion;
    private String nombreJugador;

    // Métodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        bundle = getIntent().getExtras();

        tv_titulo = findViewById(R.id.tv_titulo);
        tv_puntuacion = findViewById(R.id.tv_puntuacion);
        tx_nombreJugador = findViewById(R.id.tx_nombre);
        this.puntuacion = bundle.getInt("puntuacion");

        // Si el jugador ha ganado o ha perdido
        if (bundle.getBoolean("isGanado")) {
            tv_titulo.setText(getResources().getString(R.string.veredictoGanado));
        } else {
            tv_titulo.setText(getResources().getString(R.string.veredictoPerdido));
        }

        tv_puntuacion.setText(getResources().getString(R.string.tupuntuacion) + " " + puntuacion);
    }

    /**
     * Método que se ejecuta cuando se pulsa el botón de volver
     */
    @Override
    public void onBackPressed() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.show(getSupportFragmentManager(), "confirmDialog");
    }

    // Métodos de botones

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de guardar
     * @param v
     */
    public void onButtonGuardar(View v) {
        nombreJugador = tx_nombreJugador.getText().toString();
        DatabaseConfig.database.insertarDatos(nombreJugador, puntuacion);
        Toast.makeText(this, "La puntuación se ha guardado correctamente", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

}
