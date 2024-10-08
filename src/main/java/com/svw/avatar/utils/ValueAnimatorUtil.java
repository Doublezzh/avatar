package com.svw.avatar.utils;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * 用来重置动画缩放时长的工具类
 */
public class ValueAnimatorUtil {

    /**
     * 如果动画被禁用，则重置动画缩放时长
     */
    public static void resetDurationScaleIfDisable() {
        if (getDurationScale() == 0) {
            resetDurationScale();
        }
    }

    /**
     * 重置动画缩放时长
     */
    public static void resetDurationScale() {
        try {
            getField().setFloat(null, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static float getDurationScale() {
        try {
            return getField().getFloat(null);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @NonNull
    private static Field getField() throws NoSuchFieldException {
        @SuppressLint("SoonBlockedPrivateApi") Field field = ValueAnimator.class.getDeclaredField("sDurationScale");
        field.setAccessible(true);
        return field;
    }
}
