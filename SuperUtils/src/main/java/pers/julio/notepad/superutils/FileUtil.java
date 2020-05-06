package pers.julio.notepad.superutils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.webkit.MimeTypeMap;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 傅令杰
 */
public final class FileUtil {

    //格式化的模板
    private static final String TIME_FORMAT = "_yyyyMMdd_HHmmss";

    private static final String SDCARD_DIR = Environment.getExternalStorageDirectory().getPath();

    //默认本地上传图片目录
    public static final String UPLOAD_PHOTO_DIR = Environment.getExternalStorageDirectory().getPath() + "/a_upload_photos/";
    //网页缓存地址
    public static final String WEB_CACHE_DIR = Environment.getExternalStorageDirectory().getPath() + "/app_web_cache/";
    //系统相机目录
    public static final String CAMERA_PHOTO_DIR = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath() + "/Camera/";

    private static String getTimeFormatName(String timeFormatHeader) {
        final Date date = new Date(System.currentTimeMillis());
        //必须要加上单引号
        final SimpleDateFormat dateFormat = new SimpleDateFormat("'" + timeFormatHeader + "'" + TIME_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    /**
     * @param timeFormatHeader 格式化的头(除去时间部分)
     * @param extension        后缀名
     * @return 返回时间格式化后的文件名
     */
    public static String getFileNameByTime(String timeFormatHeader, String extension) {
        return getTimeFormatName(timeFormatHeader) + "." + extension;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File createDir(String sdcardDirName) {
        //拼接成SD卡中完整的dir
        final String dir = SDCARD_DIR + "/" + sdcardDirName + "/";
        final File fileDir = new File(dir);
        if (!fileDir.exists()) { fileDir.mkdirs(); }
        return fileDir;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File createFile(String sdcardDirName, String fileName) {
        return new File(createDir(sdcardDirName), fileName);
    }

    private static File createFileByTime(String sdcardDirName, String timeFormatHeader, String extension) {
        final String fileName = getFileNameByTime(timeFormatHeader, extension);
        return createFile(sdcardDirName, fileName);
    }

    //获取文件的MIME
    public static String getMimeType(String filePath) {
        final String extension = getExtension(filePath);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    //获取文件的后缀名
    public static String getExtension(String filePath) {
        String suffix = "";
        final File file = new File(filePath);
        final String name = file.getName();
        final int idx = name.lastIndexOf('.');
        if (idx > 0) { suffix = name.substring(idx + 1); }
        return suffix;
    }

    /**
     * 保存Bitmap到SD卡中
     *
     * @param dir      目录名,只需要写自己的相对目录名即可
     * @param compress 压缩比例 100是不压缩,值约小压缩率越高
     * @return 返回该文件
     */
    public static File saveBitmap(Bitmap mBitmap, String dir, int compress) {
        final String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { return null; }
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        File fileName = createFileByTime(dir, "DOWN_LOAD", "jpg");
        try {
            fos = new FileOutputStream(fileName);
            bos = new BufferedOutputStream(fos);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, compress, bos);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) { bos.flush(); }
                if (bos != null) { bos.close(); }
                //关闭流
                if (fos != null) { fos.flush(); }
                if (fos != null) { fos.close(); }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // QUOTE: 2020/4/10,  引用待整理，暂时屏蔽
        //refreshDCIM();
        return fileName;
    }

    public static File writeToDisk(InputStream is, String dir, String name) {
        final File file = FileUtil.createFile(dir, name);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }
            bos.flush();
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) { bos.close(); }
                if (fos != null) { fos.close(); }
                if (bis != null) { bis.close(); }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File writeToDisk(InputStream is, String dir, String prefix, String extension) {
        final File file = FileUtil.createFileByTime(dir, prefix, extension);
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            byte data[] = new byte[1024 * 4];
            int count;
            while ((count = bis.read(data)) != -1) {
                bos.write(data, 0, count);
            }
            bos.flush();
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) { bos.close(); }
                if (fos != null) { fos.close(); }
                if (bis != null) { bis.close(); }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 通知系统刷新系统相册，使照片展现出来
     */
    private static void refreshDCIM(Context context) {
        if (Build.VERSION.SDK_INT >= 19) { //兼容android4.4版本，只扫描存放照片的目录
            MediaScannerConnection.scanFile(context,
                    new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath()}, null, null);
        } else {
            //扫描整个SD卡来更新系统图库，当文件很多时用户体验不佳，且不适合4.4以上版本
            context.getApplicationContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }
    }

    /**
     * 读取raw目录中的文件,并返回为字符串!
     */
    public static String getRawFile(Context context,int id) {
        final InputStream is = context.getResources().openRawResource(id);
        final BufferedInputStream bis = new BufferedInputStream(is);
        final InputStreamReader isr = new InputStreamReader(bis);
        final BufferedReader br = new BufferedReader(isr);
        final StringBuilder stringBuilder = new StringBuilder();
        String str;
        try {
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                isr.close();
                bis.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }


    public static void setIconFont(Context context,String path, TextView textView) {
        final Typeface typeface = Typeface.createFromAsset(context.getAssets(), path);
        textView.setTypeface(typeface);
    }

    /**
     * 读取assets目录下的文件,并返回字符串
     */
    public static String getAssetsFile(Context context,String name) {
        InputStream is = null;
        BufferedInputStream bis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        StringBuilder stringBuilder = null;
        final AssetManager assetManager = context.getAssets();
        try {
            is = assetManager.open(name);
            bis = new BufferedInputStream(is);
            isr = new InputStreamReader(bis);
            br = new BufferedReader(isr);
            stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) { br.close(); }
                if (isr != null) { isr.close(); }
                if (bis != null) { bis.close(); }
                if (is != null) { is.close(); }
                assetManager.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        } else {
            return null;
        }
    }

    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            final Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 功能：已知字符串内容，输出到文件
     *
     * @param filePath 要写文件的文件路径，如：data/data/com.test/files
     * @param fileName 要写文件的文件名，如：abc.txt
     * @param string   要写文件的文件内容
     */
    public static boolean write(String filePath, String fileName, String string) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(filePath);
            // 首先判断文件夹是否存在,不存在则创建文件
            if (!file.exists() && !file.mkdirs()) { return false; }

            File fileWrite = new File(filePath + File.separator + fileName);
            // 首先判断文件是否存在,不存在则创建文件
            if (!fileWrite.exists()) {
                if (!fileWrite.createNewFile())
                    return false;
            }

            // 实例化对象：文件输出流
            fileOutputStream = new FileOutputStream(fileWrite);
            // 写入文件
            fileOutputStream.write(string.getBytes());
            // 清空输出流缓存
            fileOutputStream.flush();
            // 关闭输出流
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace(); return false;
        } finally {
            try {
                if (fileOutputStream != null) { fileOutputStream.close(); }
            } catch (IOException e) { e.printStackTrace(); }
        }
        return true;
    }

    /**
     * @param filePath$Name 要写入文件夹和文件名，如：data/data/com.test/files/abc.txt
     * @param string        要写文件的文件内容
     */
    public static boolean write(String filePath$Name, String string){
        FileOutputStream fileOutputStream = null;
        try {
            // QUOTE: 2020/4/10,  引用待整理，暂时屏蔽
            //File path = new File(Global.FILE_PATH);
            //if (!path.exists() && !path.mkdirs()) { return false; }

            File file = new File(filePath$Name);
            // 首先判断文件是否存在,不存在则创建文件
            if (!file.exists()) {
                if (!file.createNewFile())
                    return false;
            }

            // 实例化对象：文件输出流
            fileOutputStream = new FileOutputStream(file);
            // 写入文件
            fileOutputStream.write(string.getBytes());
            // 清空输出流缓存
            fileOutputStream.flush();
            // 关闭输出流
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace(); return false;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean write(String filePath$Name,byte[] bytes) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(filePath$Name);
            // 首先判断文件是否存在,不存在则创建文件
            if (!file.exists()) {
                if (!file.createNewFile())
                    return false;
            }
            // 实例化对象：文件输出流
            fileOutputStream = new FileOutputStream(file);
            // 写入文件
            fileOutputStream.write(bytes, 0, bytes.length);
            // 清空输出流缓存
            fileOutputStream.flush();
            // 关闭输出流
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace(); return false;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static byte[] readBytes(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) { return null; }
        try {
            FileInputStream fis = new FileInputStream(file);
            int size = (int) file.length();
            byte[] buf = new byte[size];
            fis.read(buf);
           /* for (int i = 0; i <size ; i++) {
               buf[i] = (byte) fis.read();
            }*/
         /* StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                sb.append(new String(buf));
            }
            sb.toString();*/
            fis.close();
            return buf;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String read(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) { return null; }
        try {
            FileInputStream fis = new FileInputStream(file);
            int size = (int) file.length();
            byte[] buf = new byte[size];
            fis.read(buf);
           /* for (int i = 0; i <size ; i++) {
               buf[i] = (byte) fis.read();
            }*/
         /* StringBuffer sb = new StringBuffer();
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = fis.read(buf)) != -1) {
                sb.append(new String(buf));
            }
            sb.toString();*/
            fis.close();
            return new String(buf);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean delFile(File file) {
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delFile(f);
            }
        }
        return file.delete();
    }
}
