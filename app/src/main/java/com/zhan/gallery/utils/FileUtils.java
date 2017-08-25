package com.zhan.gallery.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zah on 2017/6/20.
 */

public class FileUtils {
    public static final String ROOT_DIR = "qutu";

    public static File mkRootdirs() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + PrefUtil.SHARED_PREFERENCES_NAME);
        file.mkdirs();
        return file;
    }


    public static boolean exist(String path) {
        return new File(path).exists();
    }

    public static void saveContent(String fileName, String content) {
        File rootPath = mkRootdirs();
        File fileOut = new File(rootPath.getAbsolutePath(), fileName);
        if (fileOut.exists()) {
            fileOut.delete();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readContent(String fileName) {
        File rootPath = mkRootdirs();
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        File config = new File(rootPath, fileName);
        if (!config.exists()) {
            return null;
        }
        try {
            in = new FileInputStream(config);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
            }
            try {
                in.close();
            } catch (IOException e) {
            }
            reader = null;
            in = null;
        }
        return sb.toString();
    }


    public static void copyfile(File fromFile, File toFile, Boolean rewrite) {
        if (!fromFile.exists()) {
            return;
        }
        if (!fromFile.isFile()) {
            return;
        }
        if (!fromFile.canRead()) {
            return;
        }
        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }
        if (toFile.exists() && rewrite) {
            toFile.delete();
        }
        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File createTempImg(String name) {
        File rootPath = mkRootdirs();
        File fileOut = new File(rootPath.getAbsolutePath() + "/temp", name);
        if (fileOut.exists()) {
            fileOut.delete();
        }
        return fileOut;
    }

    public static void clearTemp() {
        File rootPath = mkRootdirs();
        File temp = new File(rootPath.getAbsolutePath() + "/temp");
        if (temp.isDirectory()) {
            for (File f : temp.listFiles()) {
                f.delete();
            }
        }
    }
}
