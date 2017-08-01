package com.example.luyan.smartmenu_shop.Widgt;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.luyan.smartmenu_shop.R;
import com.example.luyan.smartmenu_shop.Utils.DHDensityUtil;

import static android.view.View.TEXT_ALIGNMENT_CENTER;

/**
 * Created by luyan on 7/1/16.
 */
public class SMDialog {
    Context context;
    Dialog dialog;
    TextView titleView;
    LinearLayout buttonLayout;
    RelativeLayout messageContent;

    private int ITEM_HEIGHT = 45;
    public int TAG = 666;

    public SMDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        dialog.setContentView(R.layout.sm_alert_dialog);
        titleView = (TextView) dialog.findViewById(R.id.message_title);
        messageContent = (RelativeLayout) dialog.findViewById(R.id.message_content);
        buttonLayout = (LinearLayout) dialog.findViewById(R.id.button_layout);


    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    public void setMessage(String message) {
        TextView messageView = new TextView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        messageView.setLayoutParams(params);
        messageView.setTextColor(Color.BLACK);
        messageView.setTextSize(18);
        messageView.setPadding(40, 0, 40, 0);
        messageView.setText(message);
        messageView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        messageContent.addView(messageView);
    }

    public void setInputView(View view) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View inputView = inflater.inflate(R.layout.input_dialog, null);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.setLayoutParams(params);
        messageContent.addView(view);
    }

    public void setRadioGroup(String[] channelParams, int selectIndex, final RadioGroup.OnCheckedChangeListener listener) {

        //重新设置messageContent的高度
        messageContent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DHDensityUtil.dip2px(context, ITEM_HEIGHT * 4)));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout layout = new LinearLayout(context); //实例化布局对象
        ScrollView scrollView = new ScrollView(context);
        RadioGroup group = new RadioGroup(context); //实例化单选按钮组
        scrollView.setLayoutParams(params);
        scrollView.setPadding(DHDensityUtil.dip2px(context, 10), 0, 0, 0);
        group.setLayoutParams(params);
        //添加单选按钮
        for (int i = 0; i < channelParams.length; i++) {
            RadioButton radio = new RadioButton(context);
            radio.setId(i + TAG);
            radio.setText(channelParams[i]);
            radio.setTextSize(18);
            radio.setHeight(DHDensityUtil.dip2px(context, ITEM_HEIGHT));
            if (i == selectIndex) {
                radio.setChecked(true);
            }
            group.addView(radio);
        }

        group.setOnCheckedChangeListener(listener);

        //将单选按钮组添加到布局中
        layout.addView(group);
        scrollView.addView(layout);
        messageContent.addView(scrollView);
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setPositiveButton(String text, final View.OnClickListener listener) {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.bg_alertdialog_button);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(18);
        button.setOnClickListener(listener);
        buttonLayout.addView(button);
    }

    /**
     * 设置按钮
     *
     * @param text
     * @param listener
     */
    public void setNegativeButton(String text, final View.OnClickListener listener) {
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.bg_alertdialog_button);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(18);
        button.setOnClickListener(listener);
        if (buttonLayout.getChildCount() > 0) {
            params.setMargins(1, 0, 0, 0);
            button.setLayoutParams(params);
            buttonLayout.addView(button, 1);
        } else {
            button.setLayoutParams(params);
            buttonLayout.addView(button);
        }

    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        dialog.dismiss();
    }

    public RelativeLayout getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(RelativeLayout messageContent) {
        this.messageContent = messageContent;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }
}
