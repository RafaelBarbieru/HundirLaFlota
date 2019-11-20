package com.example.hundirlaflota.utils;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

public class Screen {

    private static Display display;

    public static int getScreenSizeX(Activity activity) {
        display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int getScreenSizeY(Activity activity) {
        display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }
}
