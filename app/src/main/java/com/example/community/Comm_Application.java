package com.example.community;

import android.app.Application;

/**
 * Author：Mark
 * Date：2016/4/5 0005
 * Tell：15006330640
 */
public class Comm_Application extends Application{
    private static Comm_Application instance;
    private static boolean isDownload;
    /**
     * 单例，返回一个实例
     *
     * @return
     */
    public static Comm_Application getInstance() {
        if (instance == null) {
//            Log.w("[ECApplication] instance is null.");
        }
        return instance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        isDownload = false;
    }

    public static boolean isDownload() {
        return isDownload;
    }

    public static void setIsDownload(boolean isDownload) {
        Comm_Application.isDownload = isDownload;
    }
}
