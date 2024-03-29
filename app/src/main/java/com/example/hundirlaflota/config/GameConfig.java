package com.example.hundirlaflota.config;

import com.example.hundirlaflota.R;

public class GameConfig {

    // Constantes

    // Códigos de dificultades
    public static final int DIFICULTAD_FACIL  = 0;
    public static final int DIFICULTAD_MEDIA  = 1;
    public static final int DIFICULTAD_DIFICIL  = 2;

    // Códigos de diálogos para su reutilización
    public static final int INFO_MENU = 0;
    public static final int INFO_PREPARACION = 1;
    public static final int INFO_PARTIDA = 2;
    public static final int INFO_ERR_SELECCIONA_BARCO = 3;
    public static final int INFO_ERR_POSICION_INVALIDA = 4;
    public static final int INFO_ERR_NO_MAS_BARCOS = 5;

    // Dimensiones de los tableros según la dificultad
    public static final int[] GRID_FACIL = {4, 4};
    public static final int[] GRID_MEDIA = {6, 6};
    public static final int[] GRID_DIFICIL = {8, 8};


    // Número de barcos necesarios según la dificultad
    public static final int[] FRAGATAS =        {3, 4, 6};
    public static final int[] BUQUES =          {1, 2, 4};
    public static final int[] PORTAAVIONES =    {0, 1, 2};

    // Número de barcos según la dificultad. El índice del array depende de la variable gameDifficulty
    public static final int[] NBARCOS = {
            FRAGATAS[DIFICULTAD_FACIL] + BUQUES[DIFICULTAD_FACIL]*2 + PORTAAVIONES[DIFICULTAD_FACIL] * 3,
            FRAGATAS[DIFICULTAD_MEDIA] + BUQUES[DIFICULTAD_MEDIA]*2 + PORTAAVIONES[DIFICULTAD_MEDIA] * 3,
            FRAGATAS[DIFICULTAD_DIFICIL] + BUQUES[DIFICULTAD_DIFICIL]*2 + PORTAAVIONES[DIFICULTAD_DIFICIL] * 3
    };

    // Códigos para los datos del tablero lógico
    public static final int DATA_AGUA = 0;
    public static final int DATA_BARCO = 1;
    public static final int DATA_HIT = 2;
    public static final int DATA_MISSED = -1;

    // Códigos para los ID de los tableros
    public static final int ID_TABLERO_JUGADOR = 0;
    public static final int ID_TABLERO_ENEMIGO = 1;

    // Puntuaciones según nivel de dificultad
    public static final int[] PUNTOS_BARCO_ACERTADO = {200, 400, 600};

    // Códigos de las imágenes para el tablero físico
    public static final int BASIC_WATER_ID = R.drawable.ic_water;
    public static final int BASIC_SHIP_ID = R.drawable.ic_ship;
    public static final int BASIC_SHIP_HIT_ID = R.drawable.ic_hit;
    public static final int BASIC_HIT_MISSED_ID = R.drawable.ic_missed;
    public static final int PREMIUM_WATER_ID = R.mipmap.ic_pwater_foreground;
    public static final int PREMIUM_SHIP_ID = R.mipmap.ic_pship_foreground;
    public static final int PREMIUM_SHIP_HIT_ID = R.mipmap.ic_phit_foreground;
    public static final int PREMIUM_HIT_MISSED_ID = R.mipmap.ic_pmissed_foreground;

    // Variables de configuración
    public static int gameDifficulty;
    public static boolean isPremiumThemeSelected = false;

}
