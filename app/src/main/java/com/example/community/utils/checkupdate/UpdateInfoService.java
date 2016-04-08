package com.example.community.utils.checkupdate;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2015/2/11 0011.
 */
public class UpdateInfoService {
    private Context context;
    public UpdateInfoService(Context context){
        this.context = context;
    }
    /**
     *
     * @param urlId 服务器路径string对应的id
     * @return 更新的信息
     */
    public UpdateInfo getUpdateInfo(int urlId) throws IOException {
        System.out.println("请求xml");
        String path = context.getResources().getString(urlId);
        System.out.println("地址："+path);
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(2000);
        conn.setRequestMethod("GET");
        InputStream in = conn.getInputStream();
        System.out.println("in:"+in);
        return UpdateInfoParser.getUpateInfo(in);
    }
}
