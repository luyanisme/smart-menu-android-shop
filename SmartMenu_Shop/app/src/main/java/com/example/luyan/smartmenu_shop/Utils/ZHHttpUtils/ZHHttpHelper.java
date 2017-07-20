package com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils;

import android.content.Context;

import com.example.luyan.smartmenu_shop.Common.MyApplication;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.NetworkUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

import java.text.MessageFormat;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Wilshion on 16/8/22.
 * [description :网络请求帮助类]
 * [version : 1.0]
 */
public class ZHHttpHelper {
    public static final String TAG = ZHHttpHelper.class.getSimpleName();
    /**
     * 默认超时时间
     */
    public static final int SOCKET_TIMEOUT = 15 * 1000;

    private static ZHHttpHelper instance;

    private static AsyncHttpClient client = new AsyncHttpClient();

    static {
//        client.setConnectTimeout(SOCKET_TIMEOUT);  //连接时间
//        client.setResponseTimeout(SOCKET_TIMEOUT); //响应时间
        client.setTimeout(SOCKET_TIMEOUT); // 设置连接超时，如果不设置，默认为10s
    }

    private PersistentCookieStore cookieStore;

    public static ZHHttpHelper getInstance() {
        if (instance == null)
            instance = new ZHHttpHelper();
        return instance;
    }

    public AsyncHttpClient getAsyncHttpClient() {
        return client;
    }

    /**
     * get方法带参数
     */
    public RequestHandle get(String url, Map<String, Object> params,
                             ZHHttpCallBack httpCallBack) {
        if (!NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
            if(httpCallBack != null)httpCallBack.onFailure(-200,MyApplication.getContext().getResources().getString(R.string.no_network),null);
            return null;
        }

        if (params != null && params.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(url);
            urlBuilder.append("?");
            String paramStr;
            for (Map.Entry<String, Object> param : params.entrySet()) {
                paramStr = MessageFormat.format("{0}={1}", param.getKey(), param.getValue());
                urlBuilder.append(paramStr);
                urlBuilder.append("&");
            }
            urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
            url = urlBuilder.toString();
        }
        RequestHandle requestHandle = client.get(url, httpCallBack);
        return requestHandle;
    }

    /**
     * post请求，带参数
     */
    public RequestHandle post(String url, RequestParams params,
                              ZHHttpCallBack httpCallBack) {
        RequestHandle requestHandle = client.post(url, params, httpCallBack);
        return requestHandle;
    }

    /**
     * post请求，传json
     */
    public RequestHandle post(Context context,String url, HttpEntity httpEntity,
                              ZHHttpCallBack httpCallBack) {
        RequestHandle requestHandle = client.post(context, url, httpEntity, "application/json", httpCallBack);
        return requestHandle;
    }

    public RequestHandle getRaw(String url, Map<String, Object> params,
                                AsyncHttpResponseHandler httpCallBack) {
        if (!NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
            //SVProgressHUD.showErrorWithStatus(ZHApplication.getInstance().getApplicationContext(), "网络不可用");
            return null;
        }

        if (params != null && params.size() > 0) {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append(url);
            urlBuilder.append("?");
            String paramStr;
            for (Map.Entry<String, Object> param : params.entrySet()) {
                String key = param.getKey();
                String value = param.getValue().toString();
                if (param.getValue() == null)
                    value = "";
                paramStr = MessageFormat.format("{0}={1}", key, value);
                urlBuilder.append(paramStr);
                urlBuilder.append("&");
            }
            urlBuilder.delete(urlBuilder.length() - 1, urlBuilder.length());
            url = urlBuilder.toString();
        }
        RequestHandle requestHandle = client.get(url, httpCallBack);
        return requestHandle;
    }

}
