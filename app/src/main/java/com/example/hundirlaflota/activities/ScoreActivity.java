package com.example.hundirlaflota.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.DatabaseConfig;

public class ScoreActivity extends AppCompatActivity {

    private TableLayout tb_puntuaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        tb_puntuaciones = findViewById(R.id.tb_puntuaciones);

        listar();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void listar() {
        TableRow[] filas;
        Cursor c = DatabaseConfig.database.getDb().rawQuery("SELECT * FROM " + DatabaseConfig.NOMBRE_TABLA_PUNTUACIONES,null);
        if (c.getCount() == 0) {
            filas = new TableRow[1];
            filas[0] = new TableRow(this);

            TextView msg = new TextView(this);
            msg.setText(getResources().getString(R.string.noHayPuntuaciones));
            msg.setMaxHeight(0);
            msg.setMinimumHeight(0);
            msg.setMinimumWidth(0);
            msg.setPadding(0,0,0,0);
            msg.setLayoutParams(new TableRow.LayoutParams(tb_puntuaciones.getLayoutParams().width, (tb_puntuaciones.getLayoutParams().height)));

            filas[0].addView(msg);
            tb_puntuaciones.addView(filas[0]);
        }
        else {
            int contador = 0;
            while(c.moveToNext()) {
                filas = new TableRow[c.getCount()];
                filas[contador] = new TableRow(this);

                TextView nombreJugador = new TextView(this);
                nombreJugador.setText(c.getString(0));

                filas[contador].addView(nombreJugador);

                TextView puntos = new TextView(this);
                puntos.setText(String.valueOf(c.getInt(1)));

                filas[contador].addView(puntos);
                tb_puntuaciones.addView(filas[contador]);
                contador++;
            }

        }

    }


}
