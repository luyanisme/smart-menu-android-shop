package com.example.luyan.smartmenu_shop.Activity.Login;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.luyan.smartmenu_shop.Metadata.RESPONSE;
import com.example.luyan.smartmenu_shop.Metadata.USERINFO;
import com.example.luyan.smartmenu_shop.Metadata.USERITEM;
import com.example.luyan.smartmenu_shop.Model.UserModel;
import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Activity.MainActivity;
import com.example.luyan.smartmenu_shop.Utils.IntentUtils;
import com.example.luyan.smartmenu_shop.Utils.AESUtils;
import com.example.luyan.smartmenu_shop.Utils.ZHHttpUtils.ZHHttpCallBack;
import com.example.luyan.smartmenu_shop.Widgt.ToastWidgt;
import com.example.luyan.smartmenu_shop.Widgt.UIEditTextWithDelete;
import com.kaopiz.kprogresshud.KProgressHUD;

public class LoginActivity extends Activity implements View.OnClickListener {

    private UIEditTextWithDelete userName;
    private UIEditTextWithDelete password;
    private CheckBox checkboxButton = null;
    private SharedPreferences usp = null;//用于记录用户名密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cleanBgOfStateBar();
        setContentView(R.layout.activity_login);
        userName = (UIEditTextWithDelete) findViewById(R.id.username);
        password = (UIEditTextWithDelete) findViewById(R.id.password);
        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        checkboxButton = (CheckBox) findViewById(R.id.remember);

        findViewById(R.id.login).setOnClickListener(this);

        usp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        userName.setText(usp.getString("uname", null));
        if (usp.getBoolean("checkboxBoolean", false))
        {
            password.setText(usp.getString("upswd", null));
            checkboxButton.setChecked(true);
        }
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
                final KProgressHUD hud = KProgressHUD.create(LoginActivity.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f);
                String userNameStr = userName.getText().toString();
                String passwordStr = password.getText().toString();
                if (userNameStr.equals("")){
                    ToastWidgt.showWithInfo(this,getResources().getString(R.string.input_username), Toast.LENGTH_SHORT);
                    return;
                }

                if (passwordStr.equals("")){
                    ToastWidgt.showWithInfo(this,getResources().getString(R.string.input_password), Toast.LENGTH_SHORT);
                    return;
                }

                SharedPreferences.Editor editor = usp.edit();
                editor.putString("uname", userNameStr);
                if (checkboxButton.isChecked())
                {
                    editor.putString("upswd", passwordStr);
                    editor.putBoolean("checkboxBoolean", true);
                    editor.commit();
                }
                else
                {
                    editor.putString("upswd", null);
                    editor.putBoolean("checkboxBoolean", false);
                    editor.commit();
                }
                hud.show();
                USERITEM useritem = new USERITEM();
                useritem.setUsername(userNameStr);
                try {
                    useritem.setPassword(AESUtils.encrypt(passwordStr, AESUtils.KEY));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                UserModel.getInstance().login(this, useritem, new ZHHttpCallBack<RESPONSE<USERINFO>>() {
                    @Override
                    public void onSuccess(int statusCode, String rawJsonResponse, RESPONSE response) {
                        hud.dismiss();
                        if (response.getStatus() == 0){
                            UserModel.getInstance().setUserinfo((USERINFO) response.getData());
                            ToastWidgt.showWithInfo(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT);
                            IntentUtils.startToActivity(LoginActivity.this, MainActivity.class);
                                /*动画效果*/
                            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                        } else {
                            ToastWidgt.showWithInfo(LoginActivity.this, response.getMsg(), Toast.LENGTH_SHORT);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String rawJsonResponse, RESPONSE response) {

                    }
                });

            break;
        }
    }
}
