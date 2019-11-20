package com.example.hundirlaflota.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.activities.PrepareActivity;
import com.example.hundirlaflota.config.GameConfig;

public class DifficultyDialog extends AppCompatDialogFragment {

    private Context context;

    public DifficultyDialog(Context context) {
        this.context = context;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] opciones = getResources().getStringArray(R.array.difficulty_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.difficulty_title)
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GameConfig.gameDifficulty = which;
                        Intent intent = new Intent(context, PrepareActivity.class);
                        startActivity(intent);
                    }
                });


        return builder.create();
    }
}
