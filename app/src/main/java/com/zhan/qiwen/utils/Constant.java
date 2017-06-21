package com.zhan.qiwen.utils;

public class Constant {
    public static final String SHARED_PREFERENCES_NAME = "qw";
    public static final String KEYSTORE_KEY_ALIAS = SHARED_PREFERENCES_NAME;
    public static final String IMEI = "imei";
    public static String VALUE_TOKEN = "";
    //    public static String ROOT_HTTP_PATH = "http://117.143.221.190:8899";
    public static String ROOT_HTTP_PATH = "http://192.168.1.17:8898";

    public static class Token {
        public static final String ACCESS_TOKEN = "access_token";
    }

    public static class User {
        public static final String ID = "id";
        public static final String AVATAR_URL = "avatar_url";
        public static final String NICKNAME = "nickname";
    }

    public static class Theme {
        public static final String SP_THEME = "theme";
        public static final int THEME_LIGHT = 1;
        public static final int THEME_NIGHT = 2;
    }


}
