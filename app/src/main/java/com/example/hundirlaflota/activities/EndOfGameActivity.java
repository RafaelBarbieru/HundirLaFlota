package com.example.hundirlaflota.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;

public class EndOfGameActivity extends AppCompatActivity {

    // Atributos
    private TextView tv_titulo;
    private TextView tv_puntuacion;

    // Métodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        tv_titulo = findViewById(R.id.tv_titulo);
        tv_puntuacion = findViewById(R.id.tv_puntuacion);

        if (GameConfig.isGanado) {
            tv_titulo.setText(getResources().getString(R.string.veredictoGanado));
        } else {
            tv_titulo.setText(getResources().getString(R.string.veredictoPerdido));
        }

        tv_puntuacion.setText(getResources().getString(R.string.tupuntuacion) + " " + GameConfig.puntuacion);
    }
}
