package com.zhan.qiwen.utils;

public class Constant {

    public static final String KEYSTORE_KEY_ALIAS = "QiWen";
    public static final String VALUE_CLIENT_ID = "5e9268a6";
    public static final String VALUE_CLIENT_SECRET =
        "1feed4136daa95b76e53236cb937b63995c725dd33cd6705639384b9b34ea130";
    public static final String VALUE_GRANT_TYPE_PASSWORD = "password";
    public static final String VALUE_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    public static final String KEY_TOKEN = "Authorization";
    public static final String VALUE_TOKEN_PREFIX = "Bearer ";

    public static String VALUE_TOKEN = "";


    public  static String DATA_SELECTED = "dataSelected";
    public  static String DATA_UNSELECTED = "dataUnselected";
    public static final String SHARED_PREFERENCES_NAME = "qiwen";

    public static String ROOT_HTTP_PATH="http://117.143.221.190:8899";

    public static class Token {
        public static final String ACCESS_TOKEN = "access_token";
        public static final String TOKEN_TYPE = "token_type";
        public static final String EXPIRES_IN = "expires_in";
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String CREATED_AT = "created_at";
    }

    public static class User {
        public static final String ID = "id";
        public static final String AVATAR_URL = "avatar_url";
        public static final String USERNAME = "username";
        public static final String NICKNAME = "nickname";
    }

    public static class Theme{
      public static  final  String  SP_THEME = "theme";
      public static  final   int THEME_LIGHT = 1;
        public static  final   int THEME_NIGHT = 2;
    }

    public static  class  API_STATUS{
        public static  final   int FAILED=-1;
        public static  final   int OK=0;
        public static  final   int NO_LOGIN=1;
//        ERR_FAILED(-1, "操作失败"), ERR_NO_ERR(0, "操作成功"), ERR_NO_LOGIN(1, "当前账号没登录"), ERR_PARAM(2, "参数异常:"), ERR_SYS(3,
//                                                                                                                          "系统错误"), ERR_USER_EXIST(4, "该用户已存在"), ERR_FILE_UPLOAD(5, "文件上传失败"), ERR_USER_NOT_EXIST(6,
//                "用户不存在"), ERR_PASSWORD(7, "密码错误"), ERR_FREUENT(8, "操作频繁"), ERR_NO_AGREE(9,
//                                                                                                "非法请求"), ERR_NOT_EXIST(10, "数据不存在"),ERR_PATH_NOT_EXIST(11, "路径不存在");

    }
}
