package com.svw.avatar.customize.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.svw.avatar.R;

public class RefreshView extends LinearLayout {

    private TextView refresh_num;

    public RefreshView(Context context) {
        this(context, null);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_refresh, this, true);
        refresh_num = findViewById(R.id.refresh_num);
    }

    public RefreshView setText(String num) {
        refresh_num.setText(num);
        return this;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    startAnimation(getActionDownAnimation());
                    break;
                case MotionEvent.ACTION_CANCEL:
                    startAnimation(getActionUpAnimation());
                    break;
                case MotionEvent.ACTION_UP:
                    Animation animation = getActionUpAnimation();
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            performClick();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    startAnimation(animation);
                    break;
            }
            return true;
        }
        return false;
    }

    private Animation getActionDownAnimation() {
        ScaleAnimation animation = new ScaleAnimation(1, .9F, 1, .9F, Animation.RELATIVE_TO_SELF, .5F, Animation.RELATIVE_TO_SELF, .5F);
        animation.setDuration(150);
        animation.setFillAfter(true);
        return animation;
    }

    private Animation getActionUpAnimation() {
        ScaleAnimation animation = new ScaleAnimation(.9F, 1, .9F, 1, Animation.RELATIVE_TO_SELF, .5F, Animation.RELATIVE_TO_SELF, .5F);
        animation.setDuration(70);
        return animation;
    }

    public String getText() {
        return refresh_num.getText().toString();
    }
}
