package com.svw.avatar.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.FrameLayout;

import com.svw.avatar.ComponentAvatarInitializer;
import com.svw.avatar.R;
import com.svw.avatar.customize.views.ClassicModeView;
import com.svw.avatar.customize.views.HomeView;
import com.svw.avatar.customize.views.LevelSelectView;
import com.svw.avatar.customize.views.LoadingView;
import com.svw.avatar.utils.AvatarAudioFocusChangeListener;
import com.svw.avatar.utils.AvatarAudioFocusManager;
import com.svw.avatar.utils.AvatarBackgroundMusicManager;
import com.svw.avatar.utils.AvatarSetting;
import com.svw.avatar.utils.LevelUtil;
import com.svw.avatar.utils.LogUtil;
import com.svw.avatar.utils.ThreadPool;
import com.svw.avatar.view.AvatarCommonDialog;


/**
 * Created by wuyr on 17-7-11 下午3:27.
 */

public class AvatarMainActivity extends BaseActivity implements AvatarAudioFocusChangeListener {

    public static final int HOME = 0, //主页
            LEVEL_SELECT = 1,//关卡选择
            CLASSIC = 2;//经典模式

    public int mCurrentStatus;
    private FrameLayout mRootView;
    private LoadingView mLoadingView;
    private HomeView mHomeView;
    private LevelSelectView mLevelSelectView;
    private ClassicModeView mClassicMode;

    public static AvatarSetting setting;

    private AvatarAudioFocusManager audioFocusManager;

    public static SharedPreferences sharedPreferences;
    public static MediaPlayer selectMusic;
    public static MediaPlayer successtMusic;
    public static MediaPlayer failMusic;

    @Override
    protected int getLayoutId() {
        return R.layout.act_main_view;
    }


    protected void initView() {
        LogUtil.setDebugOn(true);
        LogUtil.setIsShowClassName(false);



        // 设置布局内容，这里 HomeView 才会被实例化
        setContentView(R.layout.act_main_view);

        // 继续初始化其他视图和逻辑
        mRootView = findViewById(R.id.root_view);
        mLoadingView = findViewById(R.id.loading_view);
        mHomeView = findViewById(R.id.home_view);




        mHomeView.setOnButtonClickListener(new HomeView.OnButtonClickListener() {
            @Override
            public void onPigstyModeButtonClicked() {
                playEffect(selectMusic);
            }

            @Override
            public void onClassicModeButtonClicked() {
                playEffect(selectMusic);
                showClassicModeLevelSelectView();
            }

            @Override
            public boolean onSoundButtonClicked() {
                playEffect(selectMusic);
                boolean isMuted = setting.isMusicPlay;
                setting.isMusicPlay = !isMuted;
                setting.isEffectPlay = !isMuted;

                setting.saveSoundSettings(); // 保存设置

                if (isMuted) {
                    stopMusic(AvatarMainActivity.this);
                } else {
                    playMusic(AvatarMainActivity.this);
                }
                return isMuted;

            }

            @Override
            public void onExitButtonClicked() {
                playEffect(selectMusic);
                onBackPressed();
            }
        });
        // 初始化 SharedPreferences 和 AvatarSetting
        sharedPreferences = getSharedPreferences("avatarSetting", MODE_PRIVATE);
        setting = new AvatarSetting(sharedPreferences);

        // 初始化音乐
        initMusic();

        // 设置 sound_btn 的背景资源
        View soundBtn = mHomeView.findViewById(R.id.sound_btn);
        if (setting.isMusicPlay) {
            soundBtn.setBackgroundResource(R.mipmap.ic_sound);
        } else {
            soundBtn.setBackgroundResource(R.mipmap.ic_sound_close);
        }
        getAudioFocusChange();

    }


    public void initMusic() {

        selectMusic = MediaPlayer.create(this, R.raw.game_click_btn);
        selectMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        selectMusic.setVolume(1f, 1f);


        successtMusic = MediaPlayer.create(this, R.raw.av_success);
        successtMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        successtMusic.setVolume(1f, 1f);

        failMusic = MediaPlayer.create(this, R.raw.av_fail);
        failMusic.setAudioStreamType(AudioManager.STREAM_MUSIC);
        failMusic.setVolume(1f, 1f);


        // 应用声音设置
        if (setting.isMusicPlay) {
            playMusic(this);
        }

    }

    private void getAudioFocusChange() {

        audioFocusManager = new AvatarAudioFocusManager(this, this);
        if (audioFocusManager.requestAudioFocus()) {
            // Focused
            //判断是否正在播放
            if (!AvatarBackgroundMusicManager.getInstance(this).isBackgroundMusicPlaying()) {
                //播放
                AvatarBackgroundMusicManager.getInstance(this).playBackgroundMusic(
                        R.raw.avatar_bg,
                        true
                );
            }
            if (setting.isMusicPlay) {
                AvatarBackgroundMusicManager.getInstance(this).setBackgroundVolume(0.5f);
            } else {
                AvatarBackgroundMusicManager.getInstance(this).setBackgroundVolume(0f);
            }
        }
    }

    public static void playMusic(Context context) {
        AvatarBackgroundMusicManager.getInstance(context).setBackgroundVolume(0.5f);

    }

    public static void stopMusic(Context context) {
        AvatarBackgroundMusicManager.getInstance(context).setBackgroundVolume(0);
    }





    public static void playEffect(MediaPlayer mediaPlayer) {
        if (setting.isEffectPlay) {
            if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
            mediaPlayer.start();
        }
    }

    @Override
    protected boolean isStatusBarNeedImmerse() {
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentStatus == HOME) {
            mHomeView.startShow();
        }

    }


    /**
     * 经典模式 关卡选择
     */
    private void showClassicModeLevelSelectView() {
        if (!mLoadingView.isLoading) {
            //播放圆圈动画
            mLoadingView.startLoad(() -> {
                mCurrentStatus = LEVEL_SELECT;
                //隐藏主页view
                mHomeView.setVisibility(View.GONE);
                mHomeView.stopShow();
                //初始化关卡选择数据
                mLevelSelectView = new LevelSelectView(this);
                mLevelSelectView.setMaxLevelCount(LevelUtil.CLASSIC_MODE_MAX_LEVEL);
                mLevelSelectView.setValidLevelCount(ComponentAvatarInitializer.getCurrentClassicModeLevel(this));
                mLevelSelectView.setOnLevelSelectedListener(this::startClassicMode);
                mRootView.addView(mLevelSelectView, 0);
            });
        }
    }


    /**
     * 开始经典模式
     */
    private void startClassicMode(int level) {
        if (!mLoadingView.isLoading) {
            mLoadingView.startLoad(() -> {
                //释放关卡选择view的资源
                if (mLevelSelectView != null) {
                    mLevelSelectView.release();
                    mRootView.removeView(mLevelSelectView);
                    mLevelSelectView = null;
                }
                mCurrentStatus = CLASSIC;
                mClassicMode = new ClassicModeView(this);
                mClassicMode.setCurrentLevel(level);
                mRootView.addView(mClassicMode, 0);
            });
        }
    }

    public void showLevelSelectView() {
        if (!mLoadingView.isLoading) {
            mLoadingView.startLoad(() -> {
                mCurrentStatus = LEVEL_SELECT;
                // Hide other views
                if (mHomeView != null) {
                    mHomeView.setVisibility(View.GONE);
                    mHomeView.stopShow();
                }
                if (mClassicMode != null) {
                    mClassicMode.release();
                    mRootView.removeView(mClassicMode);
                    mClassicMode = null;
                }

                // Initialize the level select view
                if (mLevelSelectView == null) {
                    mLevelSelectView = new LevelSelectView(this);
                    mLevelSelectView.setMaxLevelCount(LevelUtil.CLASSIC_MODE_MAX_LEVEL);
                    mLevelSelectView.setValidLevelCount(ComponentAvatarInitializer.getCurrentClassicModeLevel(this));
                    mLevelSelectView.setOnLevelSelectedListener(this::startClassicMode);
                }
                mRootView.addView(mLevelSelectView, 0);
            });
        }
    }

    /**
     * 返回主页
     */
    public void backToHome() {
        switch (mCurrentStatus) {
            case LEVEL_SELECT:
                if (!mLoadingView.isLoading) {
                    mLoadingView.startLoad(() -> {
                        if (mLevelSelectView != null) {
                            mLevelSelectView.release();
                            mRootView.removeView(mLevelSelectView);
                            mLevelSelectView = null;
                        }
                        resetHomeState();
                    });
                }
                break;
            case CLASSIC:
                mClassicMode.exit(() -> mLoadingView.startLoad(() -> {
                    mRootView.removeView(mClassicMode);
                    resetHomeState();
                }));

                break;
            default:
                break;
        }
    }

    private void resetHomeState() {
        mCurrentStatus = HOME;
        mHomeView.setVisibility(View.VISIBLE);
        mHomeView.startShow();
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        //恢复播放背景音乐

    }

    @Override
    protected void onStop() {
        super.onStop();

        //停止播放小猪跑过的动画
        if (mCurrentStatus == HOME) {
            if (mHomeView != null) {
                mHomeView.stopShow();
            }

        }
    }

    @Override
    public void onBackPressed() {
        switch (mCurrentStatus) {
            case HOME:
                GameOver();
                break;
            case LEVEL_SELECT:
                showLevelSelectView();
                break;

            case CLASSIC:
                backToHome();
                break;
            default:
                break;
        }
    }

    private void GameOver() {

        final AvatarCommonDialog backDialog = new AvatarCommonDialog(AvatarMainActivity.this,
                "", "确定要退出游戏吗？");
        backDialog.setOnClickBottomListener(new AvatarCommonDialog.OnClickBottomListener() {
            @Override
            public void onPositiveClick() {
                playEffect(selectMusic);
                backDialog.dismiss();
                finish();
            }

            @Override
            public void onNegtiveClick() {
                playEffect(selectMusic);
                backDialog.dismiss();
            }
        });
        backDialog.show();
    }

    @Override
    public void finish() {
        super.finish();
        //释放全部资源

        AvatarBackgroundMusicManager.getInstance(this).end();
        if (selectMusic != null) {
            selectMusic.release();
            selectMusic = null;
        }


        ThreadPool.shutdown();

        mRootView = null;
        mLoadingView = null;

        mClassicMode = null;
        mHomeView.release();
        mHomeView = null;
    }

    @Override
    public void onFocusGain() {
        playMusic(this);
    }

    @Override
    public void onFocusLoss() {
        stopMusic(this);
    }

    @Override
    public void onFocusLossTransient() {
        AvatarBackgroundMusicManager.getInstance(this).setBackgroundVolume(0.1f);

    }

    @Override
    public void onCanDuck() {

    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioFocusManager != null) {
            audioFocusManager.abandonAudioFocus();
        }
        releaseMusicResources();

    }

    private void releaseMusicResources() {
        AvatarBackgroundMusicManager.getInstance(this).end();
        if (selectMusic != null) {
            selectMusic.release();
            selectMusic = null;
        }
    }



}