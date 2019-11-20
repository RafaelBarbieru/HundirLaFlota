package com.example.hundirlaflota.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TableLayout;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;
import com.example.hundirlaflota.utils.Grid;

public class GameActivity extends AppCompatActivity {

    private Toolbar toolbar;                                                // La barra superior
    private TableLayout tb_grid_player;                                    // El tablero físico
    private Grid grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Se crea la actionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se inicializa el tablero con el grid
        tb_grid_player = findViewById(R.id.tb_grid);
        grid = new Grid(1, 1);

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
        grid.drawGrid(this, tb_grid_player, GameConfig.playerData);
    }
}
