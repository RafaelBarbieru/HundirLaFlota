package com.example.hundirlaflota.activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.dialogs.ConfirmDialog;

public class EndOfGameActivity extends AppCompatActivity {

    // Atributos
    private TextView tv_titulo;
    private TextView tv_puntuacion;
    private Bundle bundle;

    // Métodos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);

        bundle = getIntent().getExtras();

        tv_titulo = findViewById(R.id.tv_titulo);
        tv_puntuacion = findViewById(R.id.tv_puntuacion);

        // Si el jugador ha ganado o ha perdido
        if (bundle.getBoolean("isGanado")) {
            tv_titulo.setText(getResources().getString(R.string.veredictoGanado));
        } else {
            tv_titulo.setText(getResources().getString(R.string.veredictoPerdido));
        }

        tv_puntuacion.setText(getResources().getString(R.string.tupuntuacion) + " " + bundle.getInt("puntuacion"));
    }

    /**
     * Método que se ejecuta cuando se pulsa el botón de volver
     */
    @Override
    public void onBackPressed() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.show(getSupportFragmentManager(), "confirmDialog");
    }
}
