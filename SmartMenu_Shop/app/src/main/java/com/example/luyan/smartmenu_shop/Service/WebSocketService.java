package com.example.luyan.smartmenu_shop.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.luyan.smartmenu_shop.Common.ServerConfig;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
import de.tavendo.autobahn.WebSocketOptions;

/**
 * Created by wxs on 16/8/17.
 */
public class WebSocketService extends Service {

    private static final String TAG = WebSocketService.class.getSimpleName();

    public static final String WEBSOCKET_ACTION = "WEBSOCKET_ACTION";

    private BroadcastReceiver connectionReceiver;
    public static boolean isClosed = true;
    private static WebSocketConnection webSocketConnection;
    private static WebSocketOptions options = new WebSocketOptions();
    private static boolean isExitApp = false;
    private static String websocketHost = ServerConfig.WEBSOCKET_ADDRESS; //websocket服务端的url,,,ws是协议,和http一样,我写的时候是用的我们公司的服务器所以这里不能贴出来

    public static Handler handler;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (connectionReceiver == null) {
            connectionReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                    if (networkInfo == null || !networkInfo.isAvailable()) {
                        Toast.makeText(getApplicationContext(), "网络已断开，请重新连接", Toast.LENGTH_SHORT).show();
                    } else {
                        if (webSocketConnection != null) {
                            webSocketConnection.disconnect();
                        }
                        if (isClosed) {
//                            webSocketConnect();
                        }
                    }

                }
            };

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            registerReceiver(connectionReceiver, intentFilter);
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void closeWebsocket(boolean exitApp) {
        isExitApp = exitApp;
        if (webSocketConnection != null && webSocketConnection.isConnected()) {
            webSocketConnection.disconnect();
            webSocketConnection = null;
        }
    }

    public static void webSocketConnect() {
        if (webSocketConnection == null) {
            webSocketConnection = new WebSocketConnection();
        }
        try {
            webSocketConnection.connect(websocketHost, new WebSocketHandler() {

                //websocket启动时候的回调
                @Override
                public void onOpen() {
                    Log.d("111", "open");
                    WebSocketService.isClosed = false;
                }

                //websocket接收到消息后的回调
                @Override
                public void onTextMessage(String payload) {
                    Log.d("111", "payload = " + payload);
                    Message noticeMessage = new Message();
                    noticeMessage.what = 0;
                    Bundle noticeBundle = new Bundle();
                    noticeBundle.putString("notice", payload);
                    noticeMessage.setData(noticeBundle);
                    handler.sendMessage(noticeMessage);
                }

                //websocket关闭时候的回调
                @Override
                public void onClose(int code, String reason) {
                    WebSocketService.isClosed = true;
                    Log.d("111", "code = " + code + " reason = " + reason);
                    switch (code) {
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3://手动断开连接
//                            if (!isExitApp) {
//                                webSocketConnect();
//                            }
                            break;
                        case 4:
                            break;
                        /**
                         * 由于我在这里已经对网络进行了判断,所以相关操作就不在这里做了
                         */
                        case 5://网络断开连接
//                            closeWebsocket(false);
//                            webSocketConnect();
                            break;
                    }
                }
            }, options);
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    public static void sendMsg(String s) {
        Log.d(TAG, "sendMsg = " + s);
        if (!TextUtils.isEmpty(s))
            if (webSocketConnection != null) {
                webSocketConnection.sendTextMessage(s);
            }
    }

    public void receiveMsg() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (connectionReceiver != null) {
            unregisterReceiver(connectionReceiver);
        }
    }

}
