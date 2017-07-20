package com.example.luyan.smartmenu_shop.Utils;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by luyan on 8/18/16.
 */
public class AsyncHttpUtils {
    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
        client.setTimeout(50000);
    }

    public static void get(String urlString, AsyncHttpResponseHandler res) {
        client.get(urlString, res);
    }

    public static void post(Context context, String url, HttpEntity httpEntity, ResponseHandlerInterface responseHandler) {
        client.post(context, url, httpEntity, "application/json", responseHandler);
    }

    public static void addHeader(String key, String value) {
        client.addHeader(key, value);
    }

}
