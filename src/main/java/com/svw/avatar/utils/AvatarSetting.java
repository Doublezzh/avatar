package com.svw.avatar.utils;

import static com.svw.avatar.activities.AvatarMainActivity.setting;
import static com.svw.avatar.activities.AvatarMainActivity.sharedPreferences;


import android.content.SharedPreferences;


public class AvatarSetting {
    public boolean isMusicPlay = true;
    public boolean isEffectPlay = true;


    public AvatarSetting(SharedPreferences sharedPreferences) {
        isMusicPlay = sharedPreferences.getBoolean("AisMusicPlay", true);
        isEffectPlay = sharedPreferences.getBoolean("AisEffectPlay", true);
    }


    public void saveSoundSettings() {
        // 获取 SharedPreferences 的编辑器对象
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // 保存音乐播放状态
        editor.putBoolean("AisMusicPlay", setting.isMusicPlay);
        // 保存音效播放状态
        editor.putBoolean("AisEffectPlay", setting.isEffectPlay);

        // 提交保存，使用 apply() 异步保存，避免阻塞主线程
        editor.apply();
    }

}
