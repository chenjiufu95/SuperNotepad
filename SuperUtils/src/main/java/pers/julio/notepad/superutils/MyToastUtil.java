package pers.julio.notepad.superutils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import me.drakeet.support.toast.BadTokenListener;
import me.drakeet.support.toast.ToastCompat;

/**
 * ClassName:  MyToastUtil
 * Description: TODO
 * Author;  julio_chan  2019/12/24 13:56
 */
public class MyToastUtil {

    public static void showToast(Context context, int resId) {
        String text = (String) context.getResources().getText(resId);
        showToast(context,text,true);
    }
    public static void showToast(Context context, String text) {
        showToast(context,text,true);
    }

    public static void showLongToast(Context context, int resId) {
        String text = (String) context.getResources().getText(resId);
        showToast(context,text,false);
    }
    public static void showLongToast(Context context, String text) {
        showToast(context,text,false);
    }

    public static void showToastInThread(Activity activity, int resId) {
        String text = (String) activity.getResources().getText(resId);
        showToastInThread(activity,text);
    }
    public static void showToastInThread(Activity activity, String text) {
        showToastInThread(activity,text);
    }

    /** 在子线程中显示Toast
     * @param activity
     * @param text
     * @param shortToast 显示Toast的长短
     */
    public static void showToastInThread(final Activity activity, final String text, final boolean shortToast) {
        if(activity == null) return;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(activity, text, shortToast);
            }
        });
    }

    /** UI线程中显示Toast
     * @param context
     * @param text
     * @param shortToast 显示Toast的长短
     */
    public static void showToast(Context context, String text, boolean shortToast) {
        //Android7.1.1(2) Toast崩溃解决方案  julio
        ToastCompat.makeText(context.getApplicationContext(), text, shortToast ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG)
                .setBadTokenListener(new BadTokenListener() {
                    @Override
                    public void onBadTokenCaught(@NonNull Toast toast) {
                        MyLogUtil.e("【Toast】",  toast.toString());
                    }
                }).show();
    }
}
