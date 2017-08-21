package com.zhan.gallery.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.ImageModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String db_name = "app.db";//数据文件的名字
    private static String TABLE_GALLERY = "gallery";
    private static String TABLE_GALLERY_COMMENT = "gallery_comment";

    public DBHelper(Context context) {
        super(context, db_name, null, DeviceUtils.getVersionCode());//数据库文件保存在当前应用所在包名:<包>/database/
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_GALLERY_COMMENT + " (" +
                "gallery_id  VARCHAR(50)," +
                "channel     INTEGER," +
                "jsonValue   TEXT)";//创建数据库的SQL语句
        db.execSQL(sql);//执行SQL语句

        sql = "CREATE TABLE " + TABLE_GALLERY + " (" +
                "channel     INTEGER," +
                "jsonValue   TEXT)";//创建数据库的SQL语句
        db.execSQL(sql);//执行SQL语句
    }

    /**
     * 获取gallery 评论缓存
     *
     * @param channel
     * @param gallery_id
     * @return
     */
    public List<Comment> loadGalleryComments(int channel, String gallery_id) {
        Cursor cursor = getReadableDatabase().rawQuery("select *from " + TABLE_GALLERY_COMMENT + " where gallery_id=? and channel=?", new String[]{gallery_id, String.valueOf(channel)});
        List<Comment> comments = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            String jsonValue = cursor.getString(cursor.getColumnIndex("jsonValue"));
            comments = GsonUtil.toComments(jsonValue);
            cursor.close();
        }
        return comments;
    }

    /**
     * 清理gallery 评论缓存
     */
    public void clearGalleryComments() {
        getWritableDatabase().delete(TABLE_GALLERY_COMMENT, null, null);
    }

    public void removeGalleryComments(String gallery_id) {
        getWritableDatabase().delete(TABLE_GALLERY_COMMENT, "gallery_id=?", new String[]{gallery_id});
    }

    /**
     * 缓存评论
     *
     * @param channel
     * @param gallery_id
     * @param jsonValue
     */
    public void cacheGalleryComments(int channel, String gallery_id, String jsonValue) {
        ContentValues values = new ContentValues();
        values.put("gallery_id", gallery_id);
        values.put("channel", channel);
        values.put("jsonValue", jsonValue);
        getWritableDatabase().insert(TABLE_GALLERY_COMMENT, null, values);
    }

    /**
     * 缓存gallery
     *
     * @param channel
     * @param jsonArray
     */
    public void cacheGallerys(int channel, String jsonArray) {
        clearCacheGalleries(channel);
        ContentValues values = new ContentValues();
        values.put("channel", channel);
        values.put("jsonValue", jsonArray);
        getWritableDatabase().insert(TABLE_GALLERY, null, values);
    }

    /**
     * 获取缓存gallery
     *
     * @param channel
     */
    public List<ImageModel> loadCacheData(int channel) {
        Cursor cursor = getReadableDatabase().rawQuery("select *from " + TABLE_GALLERY + " where   channel=?", new String[]{String.valueOf(channel)});
        List<ImageModel> galleries = null;
        if (cursor != null && cursor.moveToFirst()) {
            String jsonValue = cursor.getString(cursor.getColumnIndex("jsonValue"));
            galleries = GsonUtil.toImages(jsonValue);
            cursor.close();
        }
        return galleries;
    }


    public void clearCacheGalleries(int channel) {
        getWritableDatabase().delete(TABLE_GALLERY, "channel=?", new String[]{String.valueOf(channel)});
    }

    /**
     * 当数据库进行升级是调用，这里通过NUMBER值来进行判断，数据库是否升级
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE " + TABLE_GALLERY_COMMENT;
        db.execSQL(sql);
    }


}