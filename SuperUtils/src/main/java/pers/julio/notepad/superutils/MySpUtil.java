package pers.julio.notepad.superutils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/**
 * ClassName:  MySpUtil
 * Description: TODO
 * Author;  julio_chan  2020/1/16 18:23
 */
public class MySpUtil {
    public static final String FILE_SP = "Sp";

    public static final String FILE_MESH = "Mesh";
    public static final String FILE_TIMER = "Timer";
    public static final String FILE_STATE = "StateOn";
    public static final String FILE_DOHOME = "DoHome";

    public static final String KEY_Mesh_Id = "MeshId:";
    public static final String KEY_TIME_ZONE = "__TimeZone";
    public static final String KEY_STATE_TEMP = "__StateTemp";
    public static final String KEY_POWERON_TEMP = "__PowerOnTemp";
    public static final String KEY_DISPLAY_STYLE = "Display_style";

    public static final String FMAT_OTP_DELAYER_TIPS = "%1$s(%2$s)_otp_delayer_tips";
    public static final String FMAT_OTP_DELAYER_TIME = "%1$s(%2$s)_otp_delayer_time";

    public static final String FMAT_DELAYER_TIPS = "%1$s(%2$d)_delayer_tips";
    public static final String FMAT_DELAYER_TIME = "%1$s(%2$d)_delayer_time";

    public static final String FMAT_TIMER_TIPS = "%1$s(%2$d)_timer_tips";
    public static final String FMAT_TIMER_TIME = "%1$s(%2$d)_timer_time";

    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void ClearItems(Context context, String fileName) {
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
    public static void removeItem(Context context, String key) {
        removeItem(context,MySpUtil.FILE_DOHOME,key);
    }
    public static void removeItem(Context context, String fileName, String key) {
        if (context == null) return;
        SharedPreferences sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }
    public static void removeItemsByKeyword(Context context, String fileName, String keyWord) {
        Map<String, ?> map = getMap(context, fileName);
        if (map == null) return;
        Set<String> keys = map.keySet();
        if (keys == null) return;
        for (String key : keys) {
            if (key.contains(keyWord))  removeItem(context,fileName,key);
        }
    }
    public static Map<String, ?> getMap(Context context, String fileName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getAll();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void putInt(Context context, String fileName, String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.commit();
    }
    public static int getInt(Context context, String fileName, String key, int defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defValue);
    }

    public static void putInt(Context context, String key, int value) {
        putInt(context, MySpUtil.FILE_SP,key,value);
    }
    public static int getInt(Context context, String key, int defValue) {
        return getInt(context,FILE_SP,key,defValue);
    }
   //--------------------------------------------------------------------------------------------------
    public static void putString(Context context, String fileName, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(key, value);
        edit.commit();
    }
    public static String getString(Context context, String fileName, String key, String defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defValue);
    }

    public static void putString(Context context, String key, String value) {
        putString(context,FILE_SP,key,value);
    }
    public static String getString(Context context, String key, String defValue) {
        return getString(context,FILE_SP,key,defValue);
    }
    //--------------------------------------------------------------------------------------------------
    public static void putFloat(Context context,String fileName, String key, float value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putFloat(key, value);
        edit.commit();
    }
    public static float getFloat(Context context,String fileName, String key, float defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defValue);
    }
    public static void putFloat(Context context, String key, float value) {
        putFloat(context,FILE_SP,key,value);
    }
    public static float getFloat(Context context, String key, float defValue) {
        return getFloat(context,FILE_SP,key,defValue);
    }
    //--------------------------------------------------------------------------------------------------
    public static void putBoolean(Context context,String fileName, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(key, value);
        edit.commit();
    }
    public static boolean getBoolean(Context context,String fileName, String key, boolean defValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }
    public static void putBoolean(Context context, String key, boolean value) {
        putBoolean(context,FILE_SP,key,value);
    }
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        return getBoolean(context,FILE_SP,key,defValue);
    }
    //--------------------------------------------------------------------------------------------------
}
