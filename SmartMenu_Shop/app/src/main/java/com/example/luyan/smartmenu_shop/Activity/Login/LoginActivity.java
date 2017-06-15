package com.example.luyan.smartmenu_shop.Activity.Login;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Activity.MainActivity;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Widgt.UIEditTextWithDelete;

public class LoginActivity extends Activity implements View.OnClickListener {

    private UIEditTextWithDelete userName;
    private UIEditTextWithDelete password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanBgOfStateBar();
        setContentView(R.layout.activity_login);
        userName = (UIEditTextWithDelete) findViewById(R.id.username);
        password = (UIEditTextWithDelete) findViewById(R.id.password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());

        findViewById(R.id.login).setOnClickListener(this);
    }

    /*清除状态栏背景色*/
    private void cleanBgOfStateBar() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                IntentUtils.startToActivity(LoginActivity.this, MainActivity.class);
                                /*动画效果*/
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
            break;
        }
    }
}
