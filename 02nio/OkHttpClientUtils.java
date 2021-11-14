package com.andy.homework2;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Title
 * @Author qiuwei
 * @Date 2021-11-14 3:53 PM
 * @Description
 * @Since V1.0
 */
public class OkHttpClientUtils {

    public static void main(String[] args) throws Exception{

        String url ="http://localhost:8801";
        String result = run(url);
        System.out.println(result);
    }

    public static OkHttpClient okHttpClient = new OkHttpClient();

    public static String run(String url) throws Exception{
        Request request = new Request.Builder().url(url).build();

        try(Response response = okHttpClient.newCall(request).execute()) {
            return response.body().string();
        }
    }



}
