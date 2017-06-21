package com.zhan.qiwen.utils;

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
    public static File mkRootdirs() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + Constant.SHARED_PREFERENCES_NAME);
        file.mkdirs();
        return file;
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
                sb.append(line + "\n");
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
}
