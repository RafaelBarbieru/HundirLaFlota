package com.example.hundirlaflota.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.activities.MainActivity;
import com.example.hundirlaflota.config.DatabaseConfig;


public class ConfirmDialog extends AppCompatDialogFragment {
    private Context context;

    public ConfirmDialog(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(getResources().getString(R.string.info_msg_saliendo_titulo))
                .setMessage(getResources().getString(R.string.info_msg_saliendo_cuerpo))
                .setPositiveButton(getResources().getString(R.string.si), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        if (DatabaseConfig.database != null)
                            DatabaseConfig.database.cerrar();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        return builder.create();
    }
}
