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




public class AvatarWinDialog extends Dialog {
    public Button menu_bt, again_bt, next_bt;
    public TextView tv_level, tv_time, tv_num;
    public int level, time, num;


    public AvatarWinDialog(Context context, int level, int time, int num) {
        super(context, R.style.CustomDialog);
        this.level = level;
        this.time = time;
        this.num = num;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_dialog_win);
        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initView() {
        menu_bt = (Button) findViewById(R.id.menu_bt);
        again_bt = (Button) findViewById(R.id.again_bt);
        next_bt = (Button) findViewById(R.id.next_bt);


        tv_level = (TextView) findViewById(R.id.tv_level);
        tv_time = (TextView) findViewById(R.id.avatar_time);
        tv_num = (TextView) findViewById(R.id.avatar_num);


        String levelInfo = String.format("第%d关", level);
        String gamenum = String.format("%d辆", num);

        String gameTime = formatTime(time);


        tv_level.setText(levelInfo);
        tv_time.setText(gameTime);
        tv_num.setText(gamenum);


    }

    public static String formatTime(int timeInSeconds) {
        int hours = timeInSeconds / 3600;
        int minutes = (timeInSeconds % 3600) / 60;
        int seconds = timeInSeconds % 60;

        if (hours > 0) {
            return String.format("%d小时%d分%d秒", hours, minutes, seconds);
        } else if (minutes > 0) {
            return String.format("%d分%d秒", minutes, seconds);
        } else {
            return String.format("%d秒", seconds);
        }
    }

    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        again_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickBottomListener != null) {
                    onClickBottomListener.onAgainClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        menu_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onMenuClick();
                }
            }
        });

        //设置取消按钮被点击后，向外界提供监听
        next_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNextClick();
                }
            }
        });
    }

    public OnClickBottomListener onClickBottomListener;

    public AvatarWinDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onAgainClick();

        /**
         * 点击取消按钮事件
         */
        public void onNextClick();

        public void onMenuClick();
    }
}
