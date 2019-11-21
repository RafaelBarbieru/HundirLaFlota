package com.example.hundirlaflota.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;
import com.example.hundirlaflota.utils.Grid;

public class GameActivity extends AppCompatActivity implements ImageButton.OnClickListener{

    private Toolbar toolbar;                    // La barra superior
    private TableLayout tb_grid;                // El tablero físico
    private Grid grid;
    private int nBarcosJugador;
    private int nBarcosEnemigo;
    private int[][] dataEnemigo;
    private int[][] dataJugador;
    private boolean isTableroJugador = true;    // true = tablero jugador / false = tablero enemigo
    private TextView tv_barcos_restantes;
    private TextView tv_barcos_restantes_titulo;
    private TextView tv_nombre_tablero;
//comentado
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Se inicializan otros componentes
        tv_nombre_tablero = findViewById(R.id.tv_titulo_tablero);
        tv_barcos_restantes_titulo = findViewById(R.id.tv_s_barcosRestantes);
        nBarcosEnemigo = GameConfig.NBARCOS[GameConfig.gameDifficulty];
        nBarcosJugador = nBarcosEnemigo;

        // Se muestran cuántos barcos enemigos quedan
        tv_barcos_restantes = findViewById(R.id.tv_d_barcosRestantes);
        tv_barcos_restantes.setText(String.valueOf(nBarcosEnemigo));

        // Se crea la actionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se inicializa el tablero con el grid
        tb_grid = findViewById(R.id.tb_grid);
        grid = new Grid(1, 1);

        // Se asignan los datos a los tableros lógicos
        dataJugador = GameConfig.playerData;
        dataEnemigo = GameConfig.aiData;

        // Dependiendo de la dificultad elegida, el tablero cambiará sus dimensiones
        switch (GameConfig.gameDifficulty) {
            case GameConfig.DIFICULTAD_FACIL:
                grid = new Grid(GameConfig.GRID_FACIL[0], GameConfig.GRID_FACIL[1]);
                break;
            case GameConfig.DIFICULTAD_MEDIA:
                grid = new Grid(GameConfig.GRID_MEDIA[0], GameConfig.GRID_MEDIA[1]);
                break;
            case GameConfig.DIFICULTAD_DIFICIL:
                grid = new Grid(GameConfig.GRID_DIFICIL[0], GameConfig.GRID_DIFICIL[1]);
                break;
        }

        // Se dibuja el tablero
        cambiarTablero(GameConfig.ID_TABLERO_JUGADOR);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.prepare_menu, menu);
        return true;
    }

    // Métodos de los botones

    public void onButtonCambiarTablero(View v) {
        if (isTableroJugador) {
            cambiarTablero(GameConfig.ID_TABLERO_ENEMIGO);
        } else {
            cambiarTablero(GameConfig.ID_TABLERO_JUGADOR);
        }
    }

    // Métodos funcionales

    /**
     * Método que dibuja el tablero que se le asigna por parámetro
     * @param idTablero
     */
    private void cambiarTablero(int idTablero) {
        switch (idTablero) {
            case GameConfig.ID_TABLERO_ENEMIGO:
                tv_nombre_tablero.setText(R.string.titulo_tablero_enemigo);
                tv_barcos_restantes_titulo.setText(R.string.barcos_restantes_enemigo);
                tv_barcos_restantes.setText(String.valueOf(nBarcosEnemigo));
                grid.drawGrid(this, tb_grid, dataEnemigo);
                grid.setOnClickListeners(this, tb_grid);
                isTableroJugador = false;
                break;
            case GameConfig.ID_TABLERO_JUGADOR:
                tv_nombre_tablero.setText(R.string.titulo_tablero_jugador);
                tv_barcos_restantes_titulo.setText(R.string.barcos_restantes_jugador);
                tv_barcos_restantes.setText(String.valueOf(nBarcosJugador));
                grid.drawGrid(this, tb_grid, dataJugador);
                grid.setOnClickListeners(this, tb_grid);
                isTableroJugador = true;
                break;
        }
    }


    @Override
    public void onClick(View v) {
        if (!isTableroJugador) {
            TableRow trow;
            // Se recorren todas las filas
            for (int i = 0; i < tb_grid.getChildCount(); i++) {
                trow = (TableRow) tb_grid.getChildAt(i);
                // Se recorren todos los elementos de la fila
                for (int j = 0; j < trow.getChildCount(); j++) {
                    // Si el elemento de la fila se corresponde con la vista
                    if (trow.getChildAt(j) == v) {
                        if (dataEnemigo[i][j] == 1) {
                            Toast.makeText(this, "Le has dado xd", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "No le has dado puto :v", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }  else {
            Toast.makeText(this, "No puedes cambiar", Toast.LENGTH_SHORT).show();
        }
    }
}
