package com.example.community.utils.checkupdate;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015/2/11 0011.
 */
public class UpdateInfoParser {
    /**
     * 解析 xml
     *
     */
   public static UpdateInfo getUpateInfo(InputStream inputStream){
      System.out.println("解析xml");
        XmlPullParser pullParser = Xml.newPullParser();
        UpdateInfo updateInfo = new UpdateInfo();
        try {
            pullParser.setInput(inputStream,"utf-8");
            int type = pullParser.getEventType();
            System.out.println("type"+type);
            while (type != XmlPullParser.END_DOCUMENT){
                switch (type){
                    case XmlPullParser.START_TAG:
                        if("version".equals(pullParser.getName())){
                            String version = pullParser.nextText();
                            updateInfo.setVersion(version);
                        }else if("update_content".equals(pullParser.getName())){
                            String update_content = pullParser.nextText();
                            updateInfo.setUpdate_content(update_content);
                        }else if("url".equals(pullParser.getName())){
                            String url = pullParser.nextText();
                            updateInfo.setUrl(url);
                        }
                        break;
                }
                type = pullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateInfo;
    }
}
