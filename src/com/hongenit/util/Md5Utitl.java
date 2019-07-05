package com.hongenit.util;

public class Md5Utitl {

    public static String genToken(String account, String pwd, String timeStr) {
        return account + "_" + pwd + "_" + timeStr;
    }


}
