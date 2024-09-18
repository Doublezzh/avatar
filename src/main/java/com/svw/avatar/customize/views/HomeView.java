package com.svw.avatar.customize.views;

import static com.svw.avatar.activities.AvatarMainActivity.playEffect;
import static com.svw.avatar.activities.AvatarMainActivity.selectMusic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.svw.avatar.R;


/**
 * Created by wuyr on 17-12-24 下午11:24.
 */

/**
 * 主页
 */
public class HomeView extends RelativeLayout {

    private OnButtonClickListener mOnButtonClickListener;

    public HomeView(Context context) {
        this(context, null);
    }

    public HomeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_home, this, true);
        OnClickListener onClickListener = v -> {
            if (mOnButtonClickListener != null) {
                if (v.getId() == R.id.classic_mode_btn) {
                    mOnButtonClickListener.onClassicModeButtonClicked();
                } else if (v.getId() == R.id.exit_btn) {
                    mOnButtonClickListener.onExitButtonClicked();
                } else if (v.getId() == R.id.sound_btn) {
                    boolean isMuted = mOnButtonClickListener.onSoundButtonClicked();
                    v.setBackgroundResource(isMuted ? R.mipmap.ic_sound_close : R.mipmap.ic_sound);
                }
            }
        };
        findViewById(R.id.classic_mode_btn).setOnClickListener(onClickListener);
        findViewById(R.id.exit_btn).setOnClickListener(onClickListener);
        findViewById(R.id.sound_btn).setOnClickListener(onClickListener);
    }

    public void startShow() {
        //使用post是为了确保已经初始化完成
        post(() -> ((RandomPiggies) findViewById(R.id.random_piggies)).startShow());
    }

    /**
     * 停止生成随机的小猪跑过动画
     */
    public void stopShow() {
        ((RandomPiggies) findViewById(R.id.random_piggies)).stopShow();
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    public void release() {
        stopShow();
        if (mOnButtonClickListener != null) {
            mOnButtonClickListener = null;
        }
    }

    /**
     * 各个按钮的点击事件回调
     */
    public interface OnButtonClickListener {
        void onPigstyModeButtonClicked();

        void onClassicModeButtonClicked();

        boolean onSoundButtonClicked();

        void onExitButtonClicked();
    }
}
