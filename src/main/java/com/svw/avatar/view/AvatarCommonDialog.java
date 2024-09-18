package com.svw.avatar.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.svw.avatar.R;



public class AvatarCommonDialog extends Dialog {
    public Button posBtn, negBtn;
    public TextView tv_content;
    public String title, content;


    public AvatarCommonDialog(Context context, String title, String content) {
        super(context, R.style.CustomDialog);
        this.title = title;
        this.content = content;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_dialog_common);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initView() {
        posBtn = (Button) findViewById(R.id.posBtn);
        negBtn = (Button) findViewById(R.id.negBtn);
        negBtn.setText(title);

        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(content);

    }


    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        posBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    public OnClickBottomListener onClickBottomListener;

    public AvatarCommonDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick();

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }
}
