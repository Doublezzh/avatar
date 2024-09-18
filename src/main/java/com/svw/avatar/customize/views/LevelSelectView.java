package com.svw.avatar.customize.views;

import static com.svw.avatar.activities.AvatarMainActivity.playEffect;
import static com.svw.avatar.activities.AvatarMainActivity.selectMusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.svw.avatar.R;
import com.svw.avatar.activities.AvatarMainActivity;

/**
 * 关卡选择和心的view
 */
public class LevelSelectView extends LinearLayout {

    private LevelSelect mLevelSelect;//关卡选择

    private AnimationButton back;


    public LevelSelectView(Context context) {
        this(context, null);
    }

    public LevelSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_level_select, this, true);
        mLevelSelect = findViewById(R.id.level_select);
        back = findViewById(R.id.back);

        // 设置 back 按钮的点击事件
        back.setOnClickListener(v -> {
            playEffect(selectMusic);
            ((AvatarMainActivity) getContext()).backToHome();
        });

    }


    /**
     * 总关卡数
     */
    public void setMaxLevelCount(int count) {
        mLevelSelect.setMaxItemCount(count);
    }

    /**
     * 可以玩的关卡数
     */
    public void setValidLevelCount(int count) {
        mLevelSelect.setValidItemCount(count);
    }


    public void setOnLevelSelectedListener(LevelSelect.OnLevelSelectedListener levelSelectedListener) {
        mLevelSelect.setOnLevelSelectedListener(level -> {

            levelSelectedListener.onSelected(level);
            playEffect(selectMusic); // 播放一次音效
        });
    }


    public void release() {
        if (mLevelSelect != null) {
            mLevelSelect.release();
            mLevelSelect = null;
        }

    }
}
