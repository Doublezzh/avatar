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
import com.svw.avatar.customize.views.AnimationImageButton;



public class AvatarFailDialog extends Dialog {
    public AnimationImageButton negative_button, positive_button;

    public AvatarFailDialog(Context context) {
        super(context, R.style.CustomDialog);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_game_over_view);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initView() {
        positive_button = (AnimationImageButton) findViewById(R.id.one_button);
        negative_button = (AnimationImageButton) findViewById(R.id.two_button);


    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        negative_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickBottomListener != null) {
                    onClickBottomListener.onTwoClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        positive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onOneClick();
                }
            }
        });


    }

    public OnClickBottomListener onClickBottomListener;

    public AvatarFailDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        public void onOneClick();

        public void onTwoClick();

    }
}
