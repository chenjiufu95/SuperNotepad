package pers.julio.notepad.superutils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ClassName:  MyZipUtils
 * Description: TODO
 * Author;  julio_chan  2020/3/4 11:48
 */
public class ZipUtil {

    /** MD5 加密*/
    public static String MD5(String password) {
        // MessageDigest专门用于加密的类
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] result = messageDigest.digest(password.getBytes()); // 得到加密后的字符组数

            StringBuffer sb = new StringBuffer();
            for (byte b : result) {
                int num = b & 0xff; // 这里的是为了将原本是byte型的数向上提升为int型，从而使得原本的负数转为了正数
                String hex = Integer.toHexString(num); //这里将int型的数直接转换成16进制表示
                //16进制可能是为1的长度，这种情况下，需要在前面补0，
                if (hex.length() == 1) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
     //MyZipUtils.GZip("c:/mytest/test.hex", "c:/mytest/test.gz");
     //MyZipUtils.unGZip("c:/mytest/test.gz", "c:/mytest/test2.hex");
    /**
     * 压缩文件
     * @param srcFileName   压缩文件 的名字 如 "c:/mytest/test.hex"
     * @param destFileName  压缩文件 的名字 如 "c:/mytest/test.zip"
     */
    public static boolean GZip(String srcFileName, String destFileName){
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            System.out.println("压缩失败 == 源文件不存在");
            return false;
        }
        GZIPOutputStream zos = null;
        FileInputStream fis = null;
        try { //创建压缩输出流,将目标文件传入
            zos = new GZIPOutputStream(new FileOutputStream(new File(destFileName)));
            //创建文件输入流,将源文件传入
            fis = new FileInputStream(srcFile);
            byte[] buffer = new byte[1024];
            int len = -1;
            //利用IO流写入写出的形式将源文件写入到目标文件中进行压缩
            while ((len = (fis.read(buffer))) != -1) {
                zos.write(buffer, 0, len);
            }
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            try {
                zos.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 压缩文件
     * @param srcFileName   压缩文件 的名字 如 "c:/mytest/test.zip"
     * @param destFileName  压缩文件 的名字 如 "c:/mytest/test.hex"
     */
    public static boolean unGZip(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            System.out.println("解压失败 == 文件不存在");
            return false;
        }
        GZIPInputStream zis = null;
        FileOutputStream fos = null;
        try { //创建压缩输入流,传入源文件
            zis = new GZIPInputStream(new FileInputStream(srcFile));
            //创建文件输出流,传入目标文件
            fos = new FileOutputStream(new File(destFileName));
            byte[] buffer = new byte[1024];
            int len = -1;
            //利用IO流写入写出的形式将压缩源文件解压到目标文件中
            while ((len = (zis.read(buffer))) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                zis.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /** 文件解压缩:
     * @param srcFile
     * @param desFile
     * @throws IOException
     */
    public static void unGZip(File srcFile, File desFile) throws IOException {
        GZIPInputStream zis = null;
        FileOutputStream fos = null;
        try { //创建压缩输入流,传入源文件
            zis = new GZIPInputStream(new FileInputStream(srcFile));
             //创建文件输出流,传入目标文件
            fos = new FileOutputStream(desFile);
            byte[] buffer = new byte[1024];
            int len = -1;
            //利用IO流写入写出的形式将压缩源文件解压到目标文件中
            while ((len = (zis.read(buffer))) != -1) {
                fos.write(buffer, 0, len);
            }
        } finally {
            zis.close();
            fos.close();
        }
    }

    /**
     * 压缩文件
     * @param srcFileName   压缩文件 的名字 如 "c:/mytest/test.hex"
     * @param destFileName  压缩文件 的名字 如 "c:/mytest/test.zip"
     */
    public static boolean Zip(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            System.out.println("压缩失败 == 源文件不存在");
            return false;
        }
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;
        try {
            zos = new ZipOutputStream(new FileOutputStream(new File(destFileName)));
            bis = new BufferedInputStream(new FileInputStream(srcFile));
            ZipEntry entry = new ZipEntry(srcFile.getName());
            zos.putNextEntry(entry); // putNextEntry是将源文件的当前名称定位到条目数据的开始处。
            int count;
            byte[] buf = new byte[1024];
            while ((count = bis.read(buf)) != -1) {
                zos.write(buf, 0, count);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                bis.close();
                zos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 压缩文件
     * @param srcDirectory  压缩文件 所在的目录 如 "c:/mytest/"
     * @param srcFileName   压缩文件 的名字 如 "test.hex"
     * @param destFileName  压缩文件 的名字 如 "test.zip"
     */
}
