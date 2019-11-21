package com.example.hundirlaflota.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;
import com.example.hundirlaflota.dialogs.InfoDialog;
import com.example.hundirlaflota.utils.Grid;

import org.w3c.dom.Text;

public class PrepareActivity extends AppCompatActivity implements ImageButton.OnClickListener {

    private Toolbar toolbar;                                        // La barra superior
    private TableLayout tb_grid;                                    // El tablero físico
    private Grid grid;                                              // El tablero lógico
    private int[] nBarcos;                                          // El número de barcos que se deben colocar de cada tipo
    private TextView tv_nFragatas, tv_nBuques, tv_nPortaaviones;    // Los TextView de los barcos necesarios
    RadioButton rbt_fragata, rbt_buque, rbt_portaaviones;           // Los RadioButton de los diferentes tipos de barcos
    int nFragatas, nBuques, nPortaaviones;                          // El número de barcos que se deben colocar de cada tipo (pero como entero)

    /**
     * Método que se ejecuta cuando la actividad se crea
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare);

        // Se crea la actionBar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se inicializan otros componentes
        tv_nFragatas = findViewById(R.id.tv_necesarios_fragata);
        tv_nBuques = findViewById(R.id.tv_necesarios_buque);
        tv_nPortaaviones = findViewById(R.id.tv_necesarios_portaaviones);
        rbt_fragata = findViewById(R.id.rbt_fragata);
        rbt_buque = findViewById(R.id.rbt_buque);
        rbt_portaaviones = findViewById(R.id.rbt_portaaviones);
        nFragatas = Integer.parseInt(tv_nFragatas.getText().toString());
        nBuques = Integer.parseInt(tv_nBuques.getText().toString());
        nPortaaviones = Integer.parseInt(tv_nPortaaviones.getText().toString());

        // Se inicializa el tablero con el grid
        tb_grid = findViewById(R.id.tb_grid);
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
        grid.drawGrid(this, tb_grid);

        // Se asigna a cada elemento del tablero un onClickListener
        grid.setOnClickListeners(this, tb_grid);

        // Según la dificultad elegida, se inicializa nBarcos y se le asigna valores
        nBarcos = new int[3];

        // Número de fragatas
        nBarcos[0] = GameConfig.FRAGATAS[GameConfig.gameDifficulty];
        // Número de buques de guerra
        nBarcos[1] = GameConfig.BUQUES[GameConfig.gameDifficulty];
        // Número de portaaviones
        nBarcos[2] = GameConfig.PORTAAVIONES[GameConfig.gameDifficulty];

        // Se muestra el número de barcos necesarios al usuario
        tv_nFragatas.setText(String.valueOf(nBarcos[0]));
        tv_nBuques.setText(String.valueOf(nBarcos[1]));
        tv_nPortaaviones.setText(String.valueOf(nBarcos[2]));

        // Se aplica el código de color a los anteriores TextView
        setCodigoColor();

        // Se habilita o deshabilita el botón de empezar
        habilitarBotonEmpezar();

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

    // Métodos de botones

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Ayuda
     *
     * @param v
     */
    public void onButtonAyuda(View v) {
        onAyuda();
    }

    /**
     * Método que se ejecuta cuando el usuario pulsa el botón de Empezar
     * @param v
     */
    public void onButtonEmpezar(View v) {
        onEmpezar();
    }

    // Métodos funcionales

    /**
     * Método que se ejecuta cuando se quiere acceder a Ayuda
     */
    public void onAyuda() {
        InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_PREPARACION);
        infoDialog.show(getSupportFragmentManager(), "infoDialog");
    }

    /**
     * Método que se ejecuta cuando se quiere acceder a Empezar
     */
    public void onEmpezar() {
        GameConfig.playerData = grid.getData();     // Se asignan los datos del jugador a la variable global
        GameConfig.aiData = grid.generateData();    // Se asignan los datos generados del enemigo a la variable global
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    /**
     * Método que aplica un código de color a los TextView de los barcos necesarios según la dificultad:
     * ROJO - Si aún quedan barcos de ese tipo por colocar
     * VERDE - Si no quedan barcos de ese tipo por colocar
     */
    private void setCodigoColor() {
        nFragatas = Integer.parseInt(tv_nFragatas.getText().toString());
        nBuques = Integer.parseInt(tv_nBuques.getText().toString());
        nPortaaviones = Integer.parseInt(tv_nPortaaviones.getText().toString());
        if (nFragatas == 0) {
            tv_nFragatas.setTextColor(Color.GREEN);
        } else {
            tv_nFragatas.setTextColor(Color.RED);
        }

        if (nBuques == 0) {
            tv_nBuques.setTextColor(Color.GREEN);
        } else {
            tv_nBuques.setTextColor(Color.RED);
        }

        if (nPortaaviones == 0) {
            tv_nPortaaviones.setTextColor(Color.GREEN);
        } else {
            tv_nPortaaviones.setTextColor(Color.RED);
        }
    }

    /**
     * Método que comprueba que todos los barcos se hayan usados y habilita el botón de empezar
     */
    private void habilitarBotonEmpezar() {
        Button b = findViewById(R.id.btn_aceptar);
        if (nFragatas == 0 && nBuques == 0 && nPortaaviones == 0) {
            b.setEnabled(true);
        } else {
            b.setEnabled(false);
        }
    }

    /**
     * Método que se ejecuta cada vez que el usuario pulsa en una casilla al colocar sus barcos
     * @param v
     */
    @Override
    public void onClick(View v) {
        TableRow trow;

        // Se recorren todas las filas
        for (int i = 0; i < tb_grid.getChildCount(); i++) {
            trow = (TableRow) tb_grid.getChildAt(i);
            // Se recorren todos los elementos de la fila
            for (int j = 0; j < trow.getChildCount(); j++) {
                // Si el elemento de la fila se corresponde con la vista
                if (trow.getChildAt(j) == v) {

                    // Si el radioButton de la fragata ha sido seleccionado
                    if (rbt_fragata.isChecked()) {

                        // Si siguen habiendo fragatas disponibles
                        if (nFragatas > 0) {

                            // Se deshabilita el botón
                            v.setEnabled(false);

                            // Se cambia la imagen de fondo del botón
                            ((ImageButton) v).setImageResource(GameConfig.BASIC_SHIP_ID);

                            // Se accede al campo data de grid y se sustituye el valor de agua por el de barco
                            grid.setDataAtPos(i-4, j, GameConfig.DATA_BARCO);

                            // Se resta en 1 el número de fragatas disponibles
                            tv_nFragatas.setText(String.valueOf(nFragatas - 1));

                            // Se aplica el código de color a los TextView de de barcos disponibles
                            setCodigoColor();

                            // Se verifica si se habilita el botón de empezar o no
                            habilitarBotonEmpezar();
                        } else {
                            // Se muestra un error diciendo que no hay más barcos disponibles
                            InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_NO_MAS_BARCOS);
                            infoDialog.show(getSupportFragmentManager(), "infoDialog");
                        }
                    } else if (rbt_buque.isChecked()) { // Si el radioButton del buque ha sido seleccionado

                        // Si siguen habiendo buques disponibles
                        if (nBuques > 0) {

                            // Si la casilla de la derecha existe y no es un barco
                            if (trow.getChildAt(j + 1) != null && grid.getDataAtPos(i-4,j+1) != 1) {

                                // Se deshabilitan los botones
                                v.setEnabled(false);
                                trow.getChildAt(j + 1).setEnabled(false);

                                // Se cambia la imagen de fondo de los botones
                                ((ImageButton) v).setImageResource(GameConfig.BASIC_SHIP_ID);
                                ((ImageButton) trow.getChildAt(j + 1)).setImageResource(GameConfig.BASIC_SHIP_ID);

                                // Se accede al campo data de grid y se sustituye el valor de agua por el de barco
                                grid.setDataAtPos(i-4, j, GameConfig.DATA_BARCO);
                                grid.setDataAtPos(i-4, j + 1, GameConfig.DATA_BARCO);

                                // Se resta en 1 el número de buques disponibles
                                tv_nBuques.setText(String.valueOf(Integer.parseInt(tv_nBuques.getText().toString()) - 1));

                                // Se aplica el código de color a los TextView de de barcos disponibles
                                setCodigoColor();

                                // Se verifica si se habilita el botón de empezar o no
                                habilitarBotonEmpezar();
                            } else {
                                InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_POSICION_INVALIDA);
                                infoDialog.show(getSupportFragmentManager(), "infoDialog");
                            }
                        } else {
                            InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_NO_MAS_BARCOS);
                            infoDialog.show(getSupportFragmentManager(), "infoDialog");
                        }
                    } else if (rbt_portaaviones.isChecked()) { // Si el radioButton del portaaviones ha sido seleccionado

                        // Si siguen habiendo portaaviones disponibles
                        if (nPortaaviones > 0) {

                            // Si las dos casillas de la derecha existen y no son un barco
                            if (trow.getChildAt(j + 1) != null && trow.getChildAt(j + 2) != null && grid.getDataAtPos(i-4, j+1) != 1 && grid.getDataAtPos(i-4, j+2) != 1) {

                                // Se deshabilitan los botones
                                v.setEnabled(false);
                                trow.getChildAt(j + 1).setEnabled(false);
                                trow.getChildAt(j + 2).setEnabled(false);

                                // Se cambia la imagen de fondo de los botones
                                ((ImageButton) v).setImageResource(GameConfig.BASIC_SHIP_ID);
                                ((ImageButton) trow.getChildAt(j + 1)).setImageResource(GameConfig.BASIC_SHIP_ID);
                                ((ImageButton) trow.getChildAt(j + 2)).setImageResource(GameConfig.BASIC_SHIP_ID);

                                // Se accede al campo data de grid y se sustituye el valor de agua por el de barco
                                grid.setDataAtPos(i-4, j, GameConfig.DATA_BARCO);
                                grid.setDataAtPos(i-4, j + 1, GameConfig.DATA_BARCO);
                                grid.setDataAtPos(i-4, j+2, GameConfig.DATA_BARCO);

                                // Se resta en 1 el número de portaaviones disponibles
                                tv_nPortaaviones.setText(String.valueOf(Integer.parseInt(tv_nPortaaviones.getText().toString()) - 1));

                                // Se aplica el código de color a los TextView de de barcos disponibles
                                setCodigoColor();

                                // Se verifica si se habilita el botón de empezar o no
                                habilitarBotonEmpezar();
                            } else {
                                InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_POSICION_INVALIDA);
                                infoDialog.show(getSupportFragmentManager(), "infoDialog");
                            }
                        } else {
                            InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_NO_MAS_BARCOS);
                            infoDialog.show(getSupportFragmentManager(), "infoDialog");
                        }
                    } else {
                        InfoDialog infoDialog = new InfoDialog(GameConfig.INFO_ERR_SELECCIONA_BARCO);
                        infoDialog.show(getSupportFragmentManager(), "infoDialog");
                    }
                    return;
                }
            }
        }

    }
}

