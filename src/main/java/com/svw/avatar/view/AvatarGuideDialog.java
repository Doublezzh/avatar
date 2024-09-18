package com.svw.avatar.view;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

import com.svw.avatar.R;
import com.svw.avatar.customize.views.AnimationButton;



public class AvatarGuideDialog extends Dialog {


    private AnimationButton close_bt;

    private ExoPlayer player;
    private PlayerView playerView;


    public AvatarGuideDialog(Context context) {
        super(context, R.style.CustomDialog);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_guide_view);

        Window window = getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }

        setCanceledOnTouchOutside(false);
        initView();
        initEvent();
    }

    private void initView() {
        close_bt = (AnimationButton) findViewById(R.id.close);

        playerView=findViewById(R.id.player_view);
        playerView.bringToFront();

    }


    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        close_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickBottomListener != null) {
                    onClickBottomListener.onClick();
                }
            }
        });

        // 创建 ExoPlayer 实例
        player = new ExoPlayer.Builder(getContext()).build();
        playerView.setPlayer(player);

        // 准备播放的视频资源
        Uri videoUri = Uri.parse("android.resource://" + getContext().getPackageName() + "/" + R.raw.about);
        MediaItem mediaItem = MediaItem.fromUri(videoUri);

        // 将 MediaItem 设置给播放器
        player.setMediaItem(mediaItem);
        player.setRepeatMode(Player.REPEAT_MODE_ONE);

        // 准备播放器并开始播放
        player.prepare();
        player.setPlayWhenReady(true);
        playerView.setUseController(false);

    }

    public OnClickBottomListener onClickBottomListener;

    public AvatarGuideDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    // 在 AvatarGuideDialog 中添加 release 方法
    public void release() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
        onClickBottomListener = null;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
         void onClick();


    }






}
