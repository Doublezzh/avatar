package com.svw.avatar.utils;

public interface AvatarAudioFocusChangeListener {
    void onFocusGain(); //获得焦点
    void onFocusLoss();
    void onFocusLossTransient();
    void onCanDuck();
}