package com.svw.avatar.utils;

import android.app.ActivityManager;
import android.content.Context;

import com.zyn.common.LogUtils;

import java.util.List;

public class AvatarStateUtil {

    /**
     * 判断是否在前台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                        LogUtils.i("后台", appProcess.processName);
                        return true;
                    } else {
                        LogUtils.i("前台", appProcess.processName);
                        return false;
                    }
                }
            }
        }


        return false;
    }
}
