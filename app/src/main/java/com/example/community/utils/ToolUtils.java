package com.example.community.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

/**
 * Author：Mark
 * Date：2016/4/2 0002
 * Tell：15006330640
 */
public class ToolUtils {
    public static String IMAGE_PATH = Environment.getExternalStorageDirectory().getPath()+"/community/picture/";
    public static String LATITUDE = ""; // 纬度
    public static String LONGITUDE = "" ; // 经度
    public static String ADDRESS = ""; // 地址
    public static String ProviceName = "" ;  // 省
    public static String CityName = "";  // 市
    public static String DistrictName = ""; // 县
    //
    public static String apkUrl = "";
    // 本地版本号
    public static int versionCode = 1;
    // 本地版本名
    public static String versionName;

    /**
     * 获取版本号
     * @param context
     * @return
     * @throws PackageManager.NameNotFoundException
     */
    public static int getVersionCode(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        return packageInfo.versionCode;
    }

    public static String getVersionName(Context context) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        return packageInfo.versionName;
    }
}
