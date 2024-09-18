package com.svw.avatar.customize.views;

import static com.svw.avatar.activities.AvatarMainActivity.failMusic;
import static com.svw.avatar.activities.AvatarMainActivity.playEffect;
import static com.svw.avatar.activities.AvatarMainActivity.selectMusic;
import static com.svw.avatar.activities.AvatarMainActivity.successtMusic;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.svw.avatar.ComponentAvatarInitializer;
import com.svw.avatar.R;
import com.svw.avatar.activities.AvatarMainActivity;
import com.svw.avatar.utils.AvatarBackgroundMusicManager;
import com.svw.avatar.utils.LevelUtil;
import com.svw.avatar.utils.OnExitedListener;
import com.svw.avatar.view.AvatarCommonDialog;
import com.svw.avatar.view.AvatarFailDialog;
import com.svw.avatar.view.AvatarGuideDialog;
import com.svw.avatar.view.AvatarWinDialog;
import com.zyn.common.Toaster;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class ClassicModeView extends FrameLayout {

    private ClassicMode mClassicMode;//经典模式实例

    private RelativeLayout background_rl;
    private TextView mRefreshButton;//刷新按钮
    private TextView mLevelTextView;//显示关卡数的TextView
    private long mStartTime;//开始时间
    private int mCurrentLevel;//当前关卡


    private boolean isGuideDialogShown;//新手指引对话框只显示一次

    private AnimationImageButton howIg;

    private boolean isGuideDialogVisible = false;
    private AvatarGuideDialog avatarGuideDialog;
    private AvatarFailDialog avatarFailDialog;


    public ClassicModeView(@NonNull Context context) {
        this(context, null);
    }

    public ClassicModeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClassicModeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {


        LayoutInflater.from(getContext()).inflate(R.layout.view_classic_mode, this, true);
        mClassicMode = findViewById(R.id.item_group);

        background_rl = findViewById(R.id.background_rl);

        howIg = findViewById(R.id.how_ig);
        AnimationImageButton back = findViewById(R.id.image_back_ig);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AvatarMainActivity) getContext()).backToHome();
                playEffect(selectMusic);
            }
        });

        howIg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                playEffect(selectMusic);

                if (!isGuideDialogVisible) { // 检查对话框是否已经显示
                    showGuideDialog();
                }

            }
        });
        mClassicMode.setOnOverListener(new ClassicMode.OnGameOverListener() {
            @Override
            public void onWin() {
                showVictoryDialog();
            }

            @Override
            public void onLost() {
                showFailureDialog();
            }
        });


        //各个按钮的点击事件
        OnClickListener onClickListener = v -> {
            if (v.getId() == R.id.refresh_btn) {
                playEffect(selectMusic); // 播放一次音效

                resetStatus();

                setGameBg(mCurrentLevel);
                mClassicMode.setLevel(mCurrentLevel);
                mStartTime = SystemClock.uptimeMillis();


                if (mCurrentLevel > 0) {
                    mLevelTextView.setText(mCurrentLevel + "");
                }
            } else if (v.getId() == R.id.undo_btn) {
                playEffect(selectMusic);
                try {
                    int count = Integer.parseInt(((RefreshView) v).getText());
                    if (count == 1) {
                        disableUndoButton(v);
                    } else {
                        count--;
                        ((RefreshView) v).setText(String.valueOf(count));
                    }
                    mClassicMode.undo();
                } catch (NumberFormatException e) {
                    disableUndoButton(v);
                }
            }

        };
        mRefreshButton = findViewById(R.id.refresh_btn);
        RefreshView undoButton = findViewById(R.id.undo_btn);
        mRefreshButton.setOnClickListener(onClickListener);
        undoButton.setOnClickListener(onClickListener);
        mLevelTextView = findViewById(R.id.level_text);

        initGuideDialogData();

        //点击关卡按钮的时候,如果是第一次进入,则显示新手指引对话框
        if (!isGuideDialogShown) {
            isGuideDialogShown = true;

            showGuideDialog();
        }
    }


    /**
     * 新手对话框
     */
    public void initGuideDialogData() {
        isGuideDialogShown = ComponentAvatarInitializer.getSharedPreferences(getContext()).getBoolean(
                ComponentAvatarInitializer.CLASSIC_MODE_GUIDE_DIALOG_SHOWN, false);

    }

    private void showGuideDialog() {
        if (!isGuideDialogVisible) {
            isGuideDialogVisible = true;
            if (avatarGuideDialog == null) {
                avatarGuideDialog = new AvatarGuideDialog(getContext());
                avatarGuideDialog.setOnClickBottomListener(new AvatarGuideDialog.OnClickBottomListener() {
                    @Override
                    public void onClick() {

                        playEffect(selectMusic);
                        if (avatarGuideDialog != null) {
                            avatarGuideDialog.dismiss();
                        }


                        isGuideDialogVisible = false;
                        ComponentAvatarInitializer.getSharedPreferences(getContext()).edit().putBoolean(
                                ComponentAvatarInitializer.CLASSIC_MODE_GUIDE_DIALOG_SHOWN, true).apply();


                    }
                });

            }
            avatarGuideDialog.show();

        }


    }

    private void setGameBg(int mCurrentLevel) {
        Set<Integer> firstSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 6, 9, 10, 11, 12, 13));
        Set<Integer> secondSet = new HashSet<>(Arrays.asList(5, 8, 14));

        if (firstSet.contains(mCurrentLevel)) {
            background_rl.setBackgroundResource(R.mipmap.avatar_bg);
        } else if (secondSet.contains(mCurrentLevel)) {
            background_rl.setBackgroundResource(R.mipmap.avatar_bg_three);
        } else {
            background_rl.setBackgroundResource(R.mipmap.avatar_bg_two);
        }

    }

    //设置为不可用状态
    private void disableUndoButton(View v) {
        v.setEnabled(false);
        ((RefreshView) v).setText("0");
    }


    //重新开始的时候,重置下各个按钮的状态
    private void resetStatus() {
        RefreshView undoBtn = findViewById(R.id.undo_btn);
        undoBtn.setEnabled(true);
        undoBtn.setText("3");
        mRefreshButton.setEnabled(true);
        mRefreshButton.setBackgroundResource(R.mipmap.ic_refresh);
    }


    public void setCurrentLevel(int currentLevel) {
        mCurrentLevel = currentLevel;
        if (mCurrentLevel > LevelUtil.CLASSIC_MODE_MAX_LEVEL) {
            mCurrentLevel = -1;
        }
        // 这里直接调用方法，而不是触发点击事件
        refreshGame();

    }

    private void refreshGame() {
        resetStatus();

        setGameBg(mCurrentLevel);
        mClassicMode.setLevel(mCurrentLevel);
        mStartTime = SystemClock.uptimeMillis();


        if (mCurrentLevel > 0) {
            mLevelTextView.setText(mCurrentLevel + "");
        }


    }

    public void release() {
        mClassicMode.release();
        avatarFailDialog = null;
        if (avatarGuideDialog!=null){
            avatarGuideDialog.release();
            avatarGuideDialog = null;
        }
    }

    private void showVictoryDialog() {
        ComponentAvatarInitializer.saveCurrentClassicModeLevel(getContext(), mCurrentLevel + 1);
        initGameResultDialog(false);
    }

    private void showFailureDialog() {
        initGameResultDialog(true);
        avatarFailDialog.show();
    }

    private void initGameResultDialog(boolean isRequestHelp) {


        //暂停背景音乐
        AvatarBackgroundMusicManager.getInstance(getContext()).pauseBackgroundMusic();
        if (isRequestHelp) {
            //播放成功音效
            playEffect(failMusic);  // 播放按钮点击音效
        } else {
            playEffect(successtMusic);  // 播放按钮点击音效

        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AvatarBackgroundMusicManager.getInstance(getContext()).resumeBackgroundMusic();
            }
        }, 2500);


        if (isRequestHelp) {
            avatarFailDialog = new AvatarFailDialog(getContext());
            avatarFailDialog.setOnClickBottomListener(new AvatarFailDialog.OnClickBottomListener() {
                @Override
                public void onOneClick() {
                    playEffect(selectMusic);
                    avatarFailDialog.dismiss();
                    ((AvatarMainActivity) getContext()).showLevelSelectView();

                }

                @Override
                public void onTwoClick() {
                    playEffect(selectMusic);
                    avatarFailDialog.dismiss();
                    //     mRefreshButton.performClick();
                    refreshGame();
                }
            });


        } else {


            if (mCurrentLevel < 15) {
                int elapsedTimeInSeconds = (int) ((SystemClock.uptimeMillis() - mStartTime) / 1000);
                int num = mClassicMode.getHistorySize();
                final AvatarWinDialog winDialog = new AvatarWinDialog(getContext(), mCurrentLevel, elapsedTimeInSeconds, num);
                winDialog.setOnClickBottomListener(new AvatarWinDialog.OnClickBottomListener() {

                    @Override
                    public void onAgainClick() {
                        playEffect(selectMusic);
                        winDialog.dismiss();
                        // mRefreshButton.performClick();
                        refreshGame();
                    }

                    @Override
                    public void onNextClick() {
                        playEffect(selectMusic);
                        winDialog.dismiss();

                        if (mCurrentLevel > 0) {
                            mCurrentLevel++;
                        }
                        //   mRefreshButton.performClick();
                        refreshGame();
                    }

                    @Override
                    public void onMenuClick() {
                        playEffect(selectMusic);

                        winDialog.dismiss();
                        ((AvatarMainActivity) getContext()).showLevelSelectView();
                    }
                });
                winDialog.show();
            } else {
                Toaster.showShort("恭喜通关");
            }


        }

    }


    private boolean checkNavigationCountIsEnough() {
        boolean isEnough = ComponentAvatarInitializer.getClassicModeCurrentValidNavigationCount(getContext()) > 0;
        if (isEnough) {
            ComponentAvatarInitializer.saveClassicModeCurrentValidNavigationCount(getContext(), ComponentAvatarInitializer.getClassicModeCurrentValidNavigationCount(getContext()) - 1);
        }
        return isEnough;
    }

    private boolean checkDragCountIsEnough(boolean isUpdateCount) {
        boolean isEnough = ComponentAvatarInitializer.getClassicModeCurrentValidDragCount(getContext()) > 0;
        if (isUpdateCount && isEnough) {
            ComponentAvatarInitializer.saveClassicModeCurrentValidDragCount(getContext(), ComponentAvatarInitializer.getClassicModeCurrentValidDragCount(getContext()) - 1);
        }
        return isEnough;
    }


    private void initExitDialog(OnExitedListener listener) {

        final AvatarCommonDialog backDialog = new AvatarCommonDialog(getContext(),
                "", "确定要退出游戏吗？");
        backDialog.setOnClickBottomListener(new AvatarCommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                playEffect(selectMusic);
                backDialog.dismiss();


                ((AvatarMainActivity) getContext()).showLevelSelectView();
            }

            @Override
            public void onNegtiveClick() {
                playEffect(selectMusic);
                backDialog.dismiss();
            }
        });
        backDialog.show();


    }

    public void exit(OnExitedListener listener) {
        initExitDialog(listener);
    }


}
