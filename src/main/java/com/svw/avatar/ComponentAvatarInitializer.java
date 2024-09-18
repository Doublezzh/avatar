package com.svw.avatar;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.zyn.common.IComponentInitializer;


public class ComponentAvatarInitializer implements IComponentInitializer {
    @Override
    public void init(Context context) {

    }


    //经典模式 新手指引对话框
    public static final String CLASSIC_MODE_GUIDE_DIALOG_SHOWN = "ClassicModeGuideDialogShown";
    //修猪圈模式 新手指引对话框
    public static final String PIGSTY_MODE_GUIDE_DIALOG_SHOWN = "PigstyModeGuideDialogShown";
    //经典模式当前关卡
    private static final String CURRENT_CLASSIC_MODE_LEVEL = "CurrentClassicModeLevel";
    //修猪圈模式当前关卡
    private static final String CURRENT_PIGSTY_MODE_LEVEL = "CurrentPigstyModeLevel";
    //修猪圈模式当前心数量
    private static final String PIGSTY_MODE_CURRENT_VALID_HEART_COUNT = "PigstyModeCurrentValidHeartCount";
    //经典模式当前心数量
    private static final String CLASSIC_MODE_CURRENT_VALID_DRAG_COUNT = "ClassicModeCurrentValidDragCount";
    //经典模式当前心数量
    private static final String CLASSIC_MODE_CURRENT_VALID_NAVIGATION_COUNT = "ClassicModeCurrentValidNavigationCount";


    //经典模式当前心数量
    private static final String CLASSIC_MODE_CURRENT_VALID_HEART_COUNT = "ClassicModeCurrentValidHeartCount";
    //修猪圈模式上次心更新时间
    private static final String PIGSTY_MODE_HEART_LAST_UPDATE_TIME = "PigstyModeHeartLastUpdateTime";
    //经典模式上次心更新时间
    private static final String CLASSIC_MODE_HEART_LAST_UPDATE_TIME = "ClassicModeHeartLastUpdateTime";
    //经典模式-导航 上次保存时间
    private static final String CLASSIC_MODE_NAVIGATION_LAST_UPDATE_TIME = "ClassicModeNavigationLastUpdateTime";
    //经典模式-拖动 上次保存时间
    private static final String CLASSIC_MODE_DRAG_LAST_UPDATE_TIME = "ClassicModeDragLastUpdateTime";


    //经典模式-导航 最大数
    private static final long MAX_NAVIGATION_COUNT = 50000L;
    //经典模式-导航 刷新时长 1天
    private static final long NAVIGATION_RECOVER_DURATION = 86400000L;
    //经典模式-拖动 最大数
    private static final long MAX_DRAG_COUNT = 300000L;
    //经典模式-拖动 刷新时长 2天
    private static final long DRAG_RECOVER_DURATION = 172800000L;


    private static final String NAME = ComponentAvatarInitializer.class.getSimpleName();

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(NAME, MODE_PRIVATE);
    }

    /**
     * 获取经典模式当前关卡
     */
    public static int getCurrentClassicModeLevel(Context context) {
        return (int) getSharedPreferences(context).getLong(CURRENT_CLASSIC_MODE_LEVEL, 1);
    }

    /**
     * 保存经典模式当前关卡
     */
    public static void saveCurrentClassicModeLevel(Context context, long currentLevel) {
        if (getCurrentClassicModeLevel(context) < currentLevel) {
            getSharedPreferences(context).edit().putLong(CURRENT_CLASSIC_MODE_LEVEL, currentLevel).apply();
        }
    }

    /**
     * 获取修猪圈模式当前关卡
     */
    public static int getCurrentPigstyModeLevel(Context context) {
        return (int) getSharedPreferences(context).getLong(CURRENT_PIGSTY_MODE_LEVEL, 15);
    }

    /**
     * 保存修猪圈模式当前关卡
     */
    public static void saveCurrentPigstyModeLevel(Context context, long currentLevel) {
        if (getCurrentPigstyModeLevel(context) < currentLevel) {
            getSharedPreferences(context).edit().putLong(CURRENT_PIGSTY_MODE_LEVEL, currentLevel).apply();
        }
    }


    /**
     * 保存修猪圈模式当前可用心数量
     */
    public static void savePigstyModeCurrentValidHeartCount(Context context, long count) {
        getSharedPreferences(context).edit().putLong(PIGSTY_MODE_CURRENT_VALID_HEART_COUNT, count).apply();
        updatePigstyModeHeartLastSaveTime(context);
    }


    /**
     * 保存经典模式可用心数量
     */
    public static void saveClassicModeCurrentValidHeartCount(Context context, long count) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_CURRENT_VALID_HEART_COUNT, count).apply();
        updateClassicModeHeartLastSaveTime(context);
    }

    /**
     * 获取 经典模式-导航 当前可用数量
     */
    public static int getClassicModeCurrentValidNavigationCount(Context context) {
        updateClassicModeValidNavigationCount(context);
        return (int) getSharedPreferences(context).getLong(CLASSIC_MODE_CURRENT_VALID_NAVIGATION_COUNT, MAX_NAVIGATION_COUNT);
    }

    /**
     * 保存 经典模式-导航 可用数量
     */
    public static void saveClassicModeCurrentValidNavigationCount(Context context, long count) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_CURRENT_VALID_NAVIGATION_COUNT, count).apply();
        updateClassicModeNavigationLastSaveTime(context);
    }

    /**
     * 获取 经典模式-导航 当前可用数量
     */
    public static int getClassicModeCurrentValidDragCount(Context context) {
        updateClassicModeValidDragCount(context);
        return (int) getSharedPreferences(context).getLong(CLASSIC_MODE_CURRENT_VALID_DRAG_COUNT, MAX_DRAG_COUNT);
    }

    /**
     * 保存 经典模式-导航 可用数量
     */
    public static void saveClassicModeCurrentValidDragCount(Context context, long count) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_CURRENT_VALID_DRAG_COUNT, count).apply();
        updateClassicModeDragLastSaveTime(context);
    }

    /**
     * 刷新修猪圈模式上次心更新时间
     */
    private static void updatePigstyModeHeartLastSaveTime(Context context) {
        getSharedPreferences(context).edit().putLong(PIGSTY_MODE_HEART_LAST_UPDATE_TIME, System.currentTimeMillis()).apply();
    }

    /**
     * 刷新经典模式上次心更新时间
     */
    private static void updateClassicModeHeartLastSaveTime(Context context) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_HEART_LAST_UPDATE_TIME, System.currentTimeMillis()).apply();
    }

    /**
     * 刷新 经典模式-导航 上次保存时间
     */
    private static void updateClassicModeNavigationLastSaveTime(Context context) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_NAVIGATION_LAST_UPDATE_TIME, System.currentTimeMillis()).apply();
    }

    /**
     * 刷新 经典模式-拖动 上次保存时间
     */
    private static void updateClassicModeDragLastSaveTime(Context context) {
        getSharedPreferences(context).edit().putLong(CLASSIC_MODE_DRAG_LAST_UPDATE_TIME, System.currentTimeMillis()).apply();
    }




    /**
     * 刷新 经典模式-导航 可用数量
     */
    private static void updateClassicModeValidNavigationCount(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if (sharedPreferences.getLong(CLASSIC_MODE_CURRENT_VALID_NAVIGATION_COUNT, MAX_NAVIGATION_COUNT) < MAX_NAVIGATION_COUNT) {
            long intervalTime = System.currentTimeMillis() - sharedPreferences.getLong(CLASSIC_MODE_NAVIGATION_LAST_UPDATE_TIME, 0);
            if (intervalTime > NAVIGATION_RECOVER_DURATION) {
                long recoverNavigationCount = intervalTime / NAVIGATION_RECOVER_DURATION;
                if (recoverNavigationCount > 0) {
                    recoverNavigationCount += sharedPreferences.getLong(CLASSIC_MODE_CURRENT_VALID_NAVIGATION_COUNT, MAX_NAVIGATION_COUNT);
                    if (recoverNavigationCount > MAX_NAVIGATION_COUNT) {
                        recoverNavigationCount = MAX_NAVIGATION_COUNT;
                    }
                    saveClassicModeCurrentValidNavigationCount(context, recoverNavigationCount);
                }
            }
        }
    }

    /**
     * 刷新 经典模式-拖动 可用数量
     */
    private static void updateClassicModeValidDragCount(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        if (sharedPreferences.getLong(CLASSIC_MODE_CURRENT_VALID_DRAG_COUNT, MAX_DRAG_COUNT) < MAX_DRAG_COUNT) {
            long intervalTime = System.currentTimeMillis() - sharedPreferences.getLong(CLASSIC_MODE_DRAG_LAST_UPDATE_TIME, 0);
            if (intervalTime > DRAG_RECOVER_DURATION) {
                long recoverDragCount = intervalTime / DRAG_RECOVER_DURATION;
                if (recoverDragCount > 0) {
                    recoverDragCount += sharedPreferences.getLong(CLASSIC_MODE_CURRENT_VALID_DRAG_COUNT, MAX_DRAG_COUNT);
                    if (recoverDragCount > MAX_DRAG_COUNT) {
                        recoverDragCount = MAX_DRAG_COUNT;
                    }
                    saveClassicModeCurrentValidDragCount(context, recoverDragCount);
                }
            }
        }
    }


}
