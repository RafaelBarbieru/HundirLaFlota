package com.example.hundirlaflota.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;
import com.example.hundirlaflota.dialogs.ConfirmDialog;
import com.example.hundirlaflota.dialogs.InfoDialog;
import com.example.hundirlaflota.utils.Grid;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements ImageButton.OnClickListener {

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
    private TextView tv_puntos;
    private ImageView img_cambiar;
    private boolean isGanado;
    private int puntuacion = 0;                 // Puntuación que más tarde se enviará por SMS

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Se inicializan otros componentes
        tv_nombre_tablero = findViewById(R.id.tv_titulo_tablero);
        tv_barcos_restantes_titulo = findViewById(R.id.tv_s_barcosRestantes);
        nBarcosEnemigo = GameConfig.NBARCOS[GameConfig.gameDifficulty];
        nBarcosJugador = nBarcosEnemigo;
        tv_puntos = findViewById(R.id.tv_puntos);
        tv_puntos.setText(R.string.puntos + puntuacion);
        img_cambiar = findViewById(R.id.img_cambiarTablero);


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
        dataJugador = (int[][]) getIntent().getBundleExtra("bundle").getSerializable("playerData");
        dataEnemigo = (int[][]) getIntent().getBundleExtra("bundle").getSerializable("aiData");

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
        actualizarPuntuacion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.prepare_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_ayuda:
                onAyuda();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Método que se ejecuta cuando se pulsa el botón de volver
     */
    @Override
    public void onBackPressed() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this);
        confirmDialog.show(getSupportFragmentManager(), "confirmDialog");
    }

    // Métodos de los botones

    /**
     * Método que se ejecuta cuando se pulsa en el botón de cambiar de tablero
     * @param v
     */
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
     *
     * @param idTablero
     */
    private void cambiarTablero(int idTablero) {
        switch (idTablero) {
            case GameConfig.ID_TABLERO_ENEMIGO:
                tv_nombre_tablero.setText(R.string.titulo_tablero_enemigo);
                tv_barcos_restantes_titulo.setText(R.string.barcos_restantes_enemigo);
                tv_barcos_restantes.setText(String.valueOf(nBarcosEnemigo));
                grid.drawGrid(this, tb_grid, dataEnemigo, false);
                grid.setOnClickListeners(this, tb_grid);
                isTableroJugador = false;
                break;
            case GameConfig.ID_TABLERO_JUGADOR:
                tv_nombre_tablero.setText(R.string.titulo_tablero_jugador);
                tv_barcos_restantes_titulo.setText(R.string.barcos_restantes_jugador);
                tv_barcos_restantes.setText(String.valueOf(nBarcosJugador));
                grid.drawGrid(this, tb_grid, dataJugador, true);
                grid.setOnClickListeners(this, tb_grid);
                isTableroJugador = true;
                break;
        }
    }

    /**
     * Método que muestra la ayuda de la sección
     */
    private void onAyuda() {
        InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_PARTIDA);
        infoDialog.show(getSupportFragmentManager(), "infoDialog");
    }

    /**
     * Método que actualiza el contador de los barcos enemigos y te lleva a la pantalla de
     * "Has ganado" si este llega a 0
     */
    private void actualizarContadorBarcos() {
        if (nBarcosJugador > 0 && nBarcosEnemigo > 0) {
            tv_barcos_restantes.setText(String.valueOf(nBarcosEnemigo));
        } else {
            terminarPartida();
        }

    }

    /**
     * Método que termina la partida ganándola o perdiéndola
     */
    private void terminarPartida() {
        // Si el jugador se queda sin barcos, la partida se considera perdida
        if (nBarcosJugador == 0) {
            isGanado = false;
        }
        // Si el enemigo se queda sin barcos, la partida se considera ganada
        else if (nBarcosEnemigo == 0) {
            isGanado = true;
        }
        Intent intent = new Intent(this, EndOfGameActivity.class);
        intent.putExtra("puntuacion", puntuacion);
        intent.putExtra("isGanado", isGanado);
        startActivity(intent);
    }

    /**
     * Método que actualiza el tablero
     */
    private void actualizarTablero(boolean isJugador, boolean isShipsVisible) {
        if (isJugador) {
            if (isShipsVisible)
                grid.drawGrid(this, tb_grid, dataJugador, isShipsVisible);
            else
                grid.drawGrid(this, tb_grid, dataJugador, isShipsVisible);
        } else {
            if (isShipsVisible)
                grid.drawGrid(this, tb_grid, dataEnemigo, isShipsVisible);
            else
                grid.drawGrid(this, tb_grid, dataEnemigo, isShipsVisible);
            grid.setOnClickListeners(this, tb_grid);
        }


    }

    /**
     * Método que actualiza el TextView de la puntuación
     */
    private void actualizarPuntuacion() {
        tv_puntos.setText(getResources().getString(R.string.puntos) + " " + puntuacion);
    }


    /**
     * Método que se ejecuta cada vez que el jugador pulsa en una casilla (aliada o enemiga)
     * @param v
     */
    @Override
    public void onClick(View v) {
        boolean disparoCorrecto = true; // Determina si la casilla que se ha pulsado no había sido pulsada antes

        // Controla que el usuario no pulse en sus propias casillas
        if (!isTableroJugador) {
            TableRow trow;
            // Se recorren todas las filas
            for (int i = 0; i < tb_grid.getChildCount(); i++) {
                trow = (TableRow) tb_grid.getChildAt(i);
                // Se recorren todos los elementos de la fila
                for (int j = 0; j < trow.getChildCount(); j++) {
                    // Si el elemento de la fila se corresponde con la vista
                    if (trow.getChildAt(j) == v) {
                        if (dataEnemigo[i][j] != GameConfig.DATA_HIT && dataEnemigo[i][j] != GameConfig.DATA_MISSED) {
                            // Si se acierta
                            if (dataEnemigo[i][j] == GameConfig.DATA_BARCO) {
                                // Se cambia el dato de la casilla del tablero enemigo por barco hundido
                                dataEnemigo[i][j] = GameConfig.DATA_HIT;
                                // Se resta uno a los barcos del enemigo
                                nBarcosEnemigo--;
                                // Se suma a la puntuación del jugador los puntos correspondientes a la dificultad
                                puntuacion += GameConfig.PUNTOS_BARCO_ACERTADO[GameConfig.gameDifficulty];
                                // Se actualizan varios componentes de la GUI
                                actualizarContadorBarcos();
                                actualizarTablero(false, false);
                                actualizarPuntuacion();
                            }
                            // Si se falla
                            else {

                                // Se cambia el dato de la casilla del tablero enemigo por barco hundido
                                dataEnemigo[i][j] = GameConfig.DATA_MISSED;
                                // Se actualizan varios componentes de la GUI
                                actualizarTablero(false, false);
                                actualizarPuntuacion();
                            }
                            // Se considera que el disparo es correcto
                            disparoCorrecto = true;
                        } else {
                            // Se considera que el disparo no es correcto
                            Toast.makeText(this, getResources().getString(R.string.ataqueMismaPosicion), Toast.LENGTH_SHORT).show();
                            disparoCorrecto = false;
                        }

                    }
                }
            }
            // Si el disparo es correcto, el enemigo ataca
            if (disparoCorrecto) {
                enemigoAtaca();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.noPuedesCambiar), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Método que hace que la IA ataque al jugador de manera aleatoria
     */
    private void enemigoAtaca() {

        // Se crea un número aleatorio para las coordenadas x é y
        Random rn = new Random();
        int posX, posY;
        // Se sigue generando una posición hasta que la generada coincida con una sobre la que aún no se ha disparado
        do {
            posX = rn.nextInt(grid.getColumns());
            posY = rn.nextInt(grid.getRows());
        } while (dataJugador[posY][posX] == GameConfig.DATA_MISSED || dataJugador[posY][posX] == GameConfig.DATA_HIT);


        // Se recorren todas las filas
        TableRow trow;
        for (int i = 0; i < tb_grid.getChildCount(); i++) {
            trow = (TableRow) tb_grid.getChildAt(i);
            // Si la coordenada i coincide con la posición Y generada
            if (i == posY) {
                // Se recorren todas las columnas de la fila
                for (int j = 0; j < trow.getChildCount(); j++) {
                    // Si la coordenada j coincide con la posición X generada
                    if (j == posX) {
                        // Si el dato de la casilla coincide con un barco
                        if (dataJugador[i][j] == GameConfig.DATA_BARCO) {
                            // El enemigo ha acertado
                            dataJugador[i][j] = GameConfig.DATA_HIT;
                            // Se resta uno a los barcos del jugador
                            nBarcosJugador--;
                            // Se actualiza el contador gráfico del jugador
                            actualizarContadorBarcos();
                            // Se resta a la puntuación del jugador los puntos correspondientes a la dificultad
                            puntuacion -= GameConfig.PUNTOS_BARCO_ACERTADO[GameConfig.gameDifficulty];
                            // Se actualiza el contador gráfico de la puntuación
                            actualizarPuntuacion();
                            // Se avisa al jugador de que el enemigo ha acertado mediante un Toast, proporcionando las coordenadas del ataque
                            Toast.makeText(this, getResources().getString(R.string.enemigoAcertando) + " (X:" + (posX+1) + ",Y:" + (posY+1) + ")" , Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            // El enemigo ha fallado
                            dataJugador[i][j] = GameConfig.DATA_MISSED;
                            // Se avisa al jugador de que el enemigo ha fallado mediante un Toast, proporcionando las coordenadas del ataque
                            Toast.makeText(this, getResources().getString(R.string.enemigoFallando)+ " (X:" + (posX+1) + ",Y:" + (posY+1) + ")" , Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                }
            }
        }


    }
}
