package com.example.luyan.smartmenu_shop.Utils;

import android.content.Context;
import android.util.Log;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

/**
 * Created by luyan on 07/12/2017.
 */

public class SpeechUtils {
    static String AppId = "10492872";
    static String AppKey = "7d3riclMtoN9tiwkXnYiP9Bt";
    static String AppSecret = "lRC0qaCiX9KYvzA1ojiVbG0XBb082l7p";
    static SpeechSynthesizer mSpeechSynthesizer;

    public static void initSpeech(Context mContext){
        mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        mSpeechSynthesizer.setContext(mContext);
        mSpeechSynthesizer.setSpeechSynthesizerListener(new SpeechSynthesizerListener() {
            @Override
            public void onSynthesizeStart(String s) {
                Log.v("===","onSynthesizeStart");
            }

            @Override
            public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {
                Log.v("===","onSynthesizeDataArrived");
            }

            @Override
            public void onSynthesizeFinish(String s) {
                Log.v("===","onSynthesizeFinish");
            }

            @Override
            public void onSpeechStart(String s) {
                Log.v("===","onSpeechStart");
            }

            @Override
            public void onSpeechProgressChanged(String s, int i) {
                Log.v("===","onSpeechProgressChanged");
            }

            @Override
            public void onSpeechFinish(String s) {
                Log.v("===","onSpeechFinish");
            }

            @Override
            public void onError(String s, SpeechError speechError) {
                Log.v("===","onError");
            }
        });
        mSpeechSynthesizer.setAppId(AppId);
        mSpeechSynthesizer.setApiKey(AppKey,AppSecret);
        mSpeechSynthesizer.auth(TtsMode.ONLINE);
        mSpeechSynthesizer.initTts(TtsMode.ONLINE);
        mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "4");
        mSpeechSynthesizer.setStereoVolume (0, 1);
    }

    public static void speech(String string){
        mSpeechSynthesizer.speak(string);
    }

    public static void release(){
        mSpeechSynthesizer.release();
    }
}
