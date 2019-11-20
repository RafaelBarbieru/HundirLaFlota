package com.example.hundirlaflota.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;

public class InfoDialog extends AppCompatDialogFragment {

    private int codigoInfo;

    public InfoDialog(int codigoInfo) {
        this.codigoInfo = codigoInfo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (this.codigoInfo) {
            case GameConfig.INFO_MENU:
                builder.setTitle(R.string.info_title)
                        .setMessage(R.string.info_msg_menu)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
            case GameConfig.INFO_PREPARACION:
                builder.setTitle(R.string.info_title)
                        .setMessage(R.string.info_msg_prep)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
            case GameConfig.INFO_PARTIDA:
                builder.setTitle(R.string.info_title)
                        .setMessage(R.string.info_msg_partida)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
            case GameConfig.INFO_ERR_SELECCIONA_BARCO:
                builder.setTitle(R.string.info_error_title)
                        .setMessage(R.string.info_error_selecciona_barco)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
            case GameConfig.INFO_ERR_POSICION_INVALIDA:
                builder.setTitle(R.string.info_error_title)
                        .setMessage(R.string.info_error_posicion_invalida)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
            case GameConfig.INFO_ERR_NO_MAS_BARCOS:
                builder.setTitle(R.string.info_error_title)
                        .setMessage(R.string.info_error_no_mas_barcos)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                break;
        }
        return builder.create();
    }


}
