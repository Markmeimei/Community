package com.example.community.constant;

/**
 * Author：Mark
 * Date：2016/4/2 0002
 * Tell：15006330640
 */
public class ConstantURL {
    private static final String BASE_URL = "http://dgpt.sdzxkj.cn/json/";

    // 登录
    private static final String LOGIN_URL = "login.php";
    public static final String LOGIN = BASE_URL + LOGIN_URL;

    // 更新
    private static final String UPDATE_URL = "version.php";
    public static final String UPDATE = BASE_URL + UPDATE_URL;
    // 读取社区
    private static final String COMM_URL = "doing.php";
    public static final String COMM = BASE_URL + COMM_URL;

    // 提交房屋信息
    private static final String HOUSE_URL = "doing.php?act=add&uid=3";
    public static final String HOUSE = BASE_URL + HOUSE_URL;
}
