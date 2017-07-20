package com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;
import com.loopj.android.http.BaseJsonHttpResponseHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Wilshion on 16/8/22.
 * [description : 网络请求回调类]
 * [version : 1.0]
 */
public abstract  class ZHHttpCallBack<T> extends BaseJsonHttpResponseHandler<T> {


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onUserException(Throwable error) {
        super.onUserException(error);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, T response) {
        onSuccess(statusCode,rawJsonResponse,response);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, T errorResponse) {
        onFailure(statusCode,rawJsonData,errorResponse);
    }

    public abstract void onSuccess(int statusCode,String rawJsonResponse,T response);
    public abstract void onFailure(int statusCode,String rawJsonResponse,T response);

    @Override
    protected T parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
        return new Gson().fromJson(rawJsonData, getSuperclassTypeParameter(getClass()));
    }

    public static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("貌似类型不匹配啊");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }
}
