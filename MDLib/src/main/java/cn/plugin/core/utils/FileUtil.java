package cn.plugin.core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class FileUtil {
    /**
     * 使用不同方法打开不同样式的文件
     *
     * @param file
     * @return
     */
    public static String getMIMEType(File file, Context context) {
        String property = "";
        String fName = file.getName();
      /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase();
        Properties pro = new Properties();
        InputStream is = null;
        try {
            is = context.getAssets().open("mimetype.properties");
            pro.load(is);
            property = pro.getProperty(end, "*/*");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return property;
    }

    /**
     * 获取文件格式样式
     *
     * @param f
     * @return
     */
    public static String getMIMEType(File f) {
        String type;
        String fName = f.getName();
      /* 取得扩展名 */
        String end = fName.substring(fName.lastIndexOf(".") + 1).toLowerCase();
      /* 依扩展名的类型决定MimeType */
        if (end.equals("pdf")) {
            type = "application/pdf";//
        } else if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
            type = "audio/*";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video/*";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                end.equals("jpeg") || end.equals("bmp")) {
            type = "image/*";
        } else if (end.equals("apk")) {
        /* android.permission.INSTALL_PACKAGES */
            type = "application/vnd.android.package-archive";
        } else if (end.equals("xlsx") || end.equals("xls")) {
            type = "application/vnd.ms-excel";
        } else {
//        /*如果无法直接打开，就跳出软件列表给用户选择 */
            type = "*/*";
        }
        return type;
    }

    /**
     * 写入数据
     *
     * @param content
     * @param isAppend true:追加 false:覆盖
     * @return
     */
    public static void writeToTxt(String content, boolean isAppend) {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "KWELog.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStreamWriter write = null;
            BufferedWriter out = null;
            if (file != null && file.exists()) {
                try {
                    write = new OutputStreamWriter(new FileOutputStream(
                            file, isAppend), Charset.forName("gbk"));//一定要使用gbk格式
                    out = new BufferedWriter(write, 24);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            out.write(content);
            out.flush();
            out.close();
        } catch (Exception e) {
        }
    }

    /**
     * 转换成JPG格式图片 并将原照片删除
     *
     * @param pngFilePath png或者bmp照片
     * @param jpgFilePath jpg照片
     */
    public static File convertToJPG(String pngFilePath, String jpgFilePath) {
        File file = null;
        Bitmap bitmap = BitmapFactory.decodeFile(pngFilePath);
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(jpgFilePath));
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)) {
                bos.flush();
            }
            bos.close();
            file = new File(jpgFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            bitmap.recycle();
            bitmap = null;
        }
        //删除非JPG照片
//        if (!pngFilePath.equals(jpgFilePath)) {
//            File oldImg = new File(pngFilePath);
//            oldImg.delete();
//        }

        return file;
    }

    public static String getAssetsString(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
