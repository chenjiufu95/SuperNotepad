package pers.julio.notepad.superutils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * ClassName:  MyLogUtil
 * Description: TODO
 * Author;  julio_chan  2019/12/24 14:01
 */
public class MyLogUtil {
    public static String DEBUG = "【D";
    public static String DD = "【DD";
    public static String STORAGE = "【S";

    public static boolean D = true;
    public static boolean E = true;
    public static boolean LOG_TO_FILE = true;

    /** debug日志 */
    public static void d(String tag, String message) {
        if (D) Log.d(tag, message);
    }
    public static void d(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        d(tag, message);
    }
    public static void d(Context context, String format, Object... args) {
        String text = buildMessage(format, args);
        d(context,text);
    }

    /** error日志 */
    public static void e(String tag, String message, Throwable tr) {
        if (E) { Log.e(tag, message, tr); }
    }
    public static void e(Context context, String message, Throwable tr) {
        String tag = context.getClass().getSimpleName();
        e(tag, message, tr);
    }

    public static void e(String tag, String message) {
        if (E) { Log.e(tag, message); }
    }
    public static void e(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        e(tag, message);
    }

    public static void e(Context context, String format, Object... args) {
        String text = buildMessage(format, args);
        e(context, text);
    }

    public static long startLogTimeInMillis = 0;
    public static void prepareLog(String tag) {
        startLogTimeInMillis =  Calendar.getInstance().getTimeInMillis();
        d(tag, "日志计时开始：" + startLogTimeInMillis);
    }
    public static void prepareLog(Context context) {
        String tag = context.getClass().getSimpleName();
        prepareLog(tag);
    }
    /** 描述：打印这次的执行时间毫秒，需要首先调用prepareLog().*/
    public static void d(String tag, String message, boolean printTime) {
        Calendar current = Calendar.getInstance();
        long endLogTimeInMillis = current.getTimeInMillis();
        d(tag, message + ":" + (endLogTimeInMillis - startLogTimeInMillis) + "ms");
    }

    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();
        String caller = "<unknown>";
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(MyLogUtil.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);
                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s", Thread.currentThread().getId(), caller, msg);
    }

    // 自定义 debug file日志
    //public static final String FILE_DEBUG =  "/data/data/am.doit.dohome/debug.txt";
    public static void debug(String filePath,String tag, String message) {
        d(tag ,message);
        if (LOG_TO_FILE)  log2File(filePath,tag, message);
    }
    public static void debug(String filePath,Context context, String message) {
        String tag = context.getClass().getSimpleName();
        debug(filePath,tag, message);
    }

    public static void debug(String tag, String message) {
        d(tag ,message);
    }
    public static void debug(Context context, String message) {
        String tag = context.getClass().getSimpleName();
        debug(tag, message);
    }

    private  static void log2File(final String filePath, String tag, String content) {
        if (content == null) return;
        if (!createOrExistsFile(filePath)) return;
        String time = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(new Date());
        final String dateLogContent = time + " " + tag + " " + content + '\r'+ '\n';
        new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedWriter bw = null;
                try {
                    bw = new BufferedWriter(new FileWriter(filePath, true));
                    bw.write(dateLogContent);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bw.close();
                    } catch (IOException e) { e.printStackTrace(); }
                }
            }
        }).start();
    }
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }
    public static boolean createOrExistsFile(final File file) {
        if (file == null) return false;
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static File getFileByPath(final String filePath) {
        return "".equals(filePath) ? null : new File(filePath);
    }
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }
}
