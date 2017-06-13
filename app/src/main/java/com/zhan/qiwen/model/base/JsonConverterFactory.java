package com.zhan.qiwen.model.base;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.zhan.qiwen.base.ApiException;
import com.zhan.qiwen.base.HttpResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by zah on 2017/6/13.
 */

public class JsonConverterFactory<T> extends Converter.Factory {
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, JSONObject>() {
            @Override
            public JSONObject convert(ResponseBody value) throws IOException {
                try {
                    return new JSONObject(value.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                throw new ApiException(-1,"请求失败");
            }
        };
    }
}