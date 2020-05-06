package pers.julio.notepad.superutils;

import android.app.Activity;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

/**
 * @file FileName, Created by julio_chan on 2019/9/25.
 */
public class MyStatusBarUtil {
    public static void setLightStatusBar(@NonNull Activity activity, boolean lightStatusBar){
        final Window window = activity.getWindow();
        View decor = window.getDecorView();
        int ui = decor.getSystemUiVisibility();
        if (lightStatusBar) {
            ui |=View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        decor.setSystemUiVisibility(ui);
    }
}
