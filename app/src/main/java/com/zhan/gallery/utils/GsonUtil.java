package com.zhan.gallery.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import com.zhan.gallery.model.Channel;
import com.zhan.gallery.model.Comment;
import com.zhan.gallery.model.Gallery;
import com.zhan.gallery.model.User;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by zah on 2017/8/4.
 */

public class GsonUtil {
    public static List<Comment> toComments(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        return gson.fromJson(json, new TypeToken<List<Comment>>() {
        }.getType());
    }

    public static Comment toComment(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        return gson.fromJson(json, Comment.class);
    }

    public static List<Channel> toChannels(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Channel>>() {
        }.getType());
    }

    public static List<Gallery> toGalleries(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<Gallery>>() {
        }.getType());
    }

    public static User toUser(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, User.class);
    }
}
