package com.example.hundirlaflota.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.example.hundirlaflota.config.GameConfig;

import java.util.Random;

public class Grid {
    // Atributos
    private int rows;
    private int columns;
    private TableRow[] tableRows;
    private ImageButton[][] elements;
    private int[][] data;

    // Constructores
    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.elements = new ImageButton[rows][columns];
        this.tableRows = new TableRow[rows];
        data = new int[rows][columns];
        for (int i=0; i<rows; i++) {
            for (int j=0; j<columns; j++) {
                Log.wtf("DRAWDATA","Se pone un 0 en la posición i:" + i + ", j:" + j);
                data[i][j] = 0;
            }
        }
    }

    // Métodos

    /**
     * Método que dibuja el tablero vacío
     */
    public void drawGrid(Activity activity, TableLayout tableLayout) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        TableRow[] trows;
        ImageButton[] tcolumns;
        for (int i=0; i<rows; i++) {
            trows = new TableRow[rows];
            trows[i] = new TableRow(activity.getBaseContext());
            tableRows[i] = trows[i];
            tableLayout.addView(trows[i]);
            for (int j=0; j<columns; j++) {
                tcolumns = new ImageButton[columns];
                tcolumns[j] = new ImageButton(activity.getBaseContext());
                elements[i][j] = tcolumns[j];
                tcolumns[j].setMaxHeight(0);
                tcolumns[j].setMinimumHeight(0);
                tcolumns[j].setMinimumWidth(0);
                tcolumns[j].setLayoutParams(new TableRow.LayoutParams(screenWidth/columns, screenHeight/rows));
                tcolumns[j].setPadding(0,0,0,0);
                tcolumns[j].setImageResource(GameConfig.BASIC_WATER_ID);
                tcolumns[j].setLayoutParams(new TableRow.LayoutParams(tableLayout.getLayoutParams().width / columns, (tableLayout.getLayoutParams().height * 87 / 100) / rows));
                trows[i].addView(tcolumns[j]);
            }
        }
    }

    /**
     * Método que dibuja el tablero con barcos
     * @param activity
     * @param tableLayout
     * @param data
     */
    public void drawGrid(Activity activity, TableLayout tableLayout, int[][] data) {
        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
        TableRow[] trows;
        ImageButton[] tcolumns;
        for (int i=0; i<rows; i++) {
            trows = new TableRow[rows];
            trows[i] = new TableRow(activity.getBaseContext());
            tableRows[i] = trows[i];
            tableLayout.addView(trows[i]);
            for (int j=0; j<columns; j++) {
                tcolumns = new ImageButton[columns];
                tcolumns[j] = new ImageButton(activity.getBaseContext());
                elements[i][j] = tcolumns[j];
                tcolumns[j].setMaxHeight(0);
                tcolumns[j].setMinimumHeight(0);
                tcolumns[j].setMinimumWidth(0);
                tcolumns[j].setLayoutParams(new TableRow.LayoutParams(screenWidth/columns, screenHeight/rows));
                tcolumns[j].setPadding(0,0,0,0);
                if (data[i][j] == 1) {
                    tcolumns[j].setImageResource(GameConfig.BASIC_SHIP_ID);
                } else {
                    tcolumns[j].setImageResource(GameConfig.BASIC_WATER_ID);
                }
                tcolumns[j].setLayoutParams(new TableRow.LayoutParams(tableLayout.getLayoutParams().width / columns, (tableLayout.getLayoutParams().height * 87 / 100) / rows));
                trows[i].addView(tcolumns[j]);
            }
        }
    }

    // Getters
    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public ImageButton[][] getElements() {
        return this.elements;
    }

    public TableRow[] getTableRows() {
        return this.tableRows;
    }

    public TableRow getTableRow(int row) {
        return this.tableRows[row];
    }

    public ImageButton getElement(int row, int column) {
        return this.elements[row][column];
    }

    public int[][] getData() {
        return this.data;
    }

    /**
     * Método que devuelve el número lógico perteneciente a una posición del tablero lógico
     * @param row       Fila
     * @param column    Columna
     * @return
     */
    public int getDataAtPos(int row, int column) {
        return this.data[row][column];
    }

    // Setters
    /**
     * Método que aplica un número lógico a una posición del tablero lógico
     * @param row       Fila
     * @param column    Columna
     * @param data      El valor lógico
     */
    public void setDataAtPos(int row, int column, int data) {
        this.data[row][column] = data;
    }

    /**
     * Método que genera datos para el tablero del enemigo
     */
    public int[][] generateData() {
        int[][] data = new int[rows][columns];
        Random r = new Random();
        switch (GameConfig.gameDifficulty) {
            case GameConfig.DIFICULTAD_FACIL:
                for (int i=0; i<GameConfig.NBARCOS_FACIL; i++) {
                    data[r.nextInt(rows)][r.nextInt(columns)] = r.nextInt(1);
                }
                break;
            case GameConfig.DIFICULTAD_MEDIA:
                for (int i=0; i<GameConfig.NBARCOS_MEDIA; i++) {
                    data[r.nextInt(rows)][r.nextInt(columns)] = r.nextInt(1);
                }
                break;
            case GameConfig.DIFICULTAD_DIFICIL:
                for (int i=0; i<GameConfig.NBARCOS_DIFICIL; i++) {
                    data[r.nextInt(rows)][r.nextInt(columns)] = r.nextInt(1);
                }
                break;
        }
        return data;
    }
}
