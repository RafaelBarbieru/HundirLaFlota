package com.example.hundirlaflota.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hundirlaflota.R;
import com.example.hundirlaflota.config.GameConfig;

public class ThemeDialog extends AppCompatDialogFragment {

    private Context mainContext;

    public ThemeDialog(Context context) {
        this.mainContext = context;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String[] opciones = getResources().getStringArray(R.array.theme_options);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.theme_title)
                .setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            GameConfig.isPremiumThemeSelected = false;
                            Toast.makeText(mainContext, R.string.theme_basic_toast, Toast.LENGTH_SHORT).show();
                        } else if (which == 1) {
                            GameConfig.isPremiumThemeSelected = true;
                            Toast.makeText(mainContext, R.string.theme_premium_toast, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return builder.create();
    }

}
