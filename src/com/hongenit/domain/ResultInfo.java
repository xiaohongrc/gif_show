package com.hongenit.domain;

import java.io.Serializable;

/**
 * 用于封装后端返回前端数据对象
 */
public class ResultInfo implements Serializable {

    // 状态码常量
    public static final int STATUS_OK = 0;
    public static final int STATUS_REGIST_FAIL = 10001;
    public static final int STATUS_ACCOUNT_EXIST = 10002;
    public static final int STATUS_NICKNAME_EXIST = 10003;
    public static final int STATUS_USER_NOT_EXIST = 10004;
    public static final int STATUS_UPDATE_FAIL = 10005;
    public static final int STATUS_LOGIN_FAIL = 10006;
    public static final int STATUS_FETCH_USER_INFO_FAIL = 10007;


    // 消息描述常量
    public static final String MSG_OK = "OK";
    public static final String MSG_REGIST_FAIL = "注册失败";
    public static final String MSG_ACCOUNT_EXIST = "用户名已存在";
    public static final String MSG_NICKNAME_EXIST = "昵称已被使用";
    public static final String MSG_USER_NOT_EXIST = "用户不存在";
    public static final String MSG_UPDATE_FAIL = "更新信息失败";
    public static final String MSG_LOGIN_FAIL = "用户名或密码错误";
    public static final String MSG_FETCH_USER_INFO_FAIL = "无权限获取";

    private int status = STATUS_OK; // 状态码
    private Object data; //后端返回结果数据对象
    private String msg = MSG_OK; //消息描述

    //无参构造方法
    public ResultInfo() {
    }


    public ResultInfo(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ResultInfo(int status, Object data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResultInfo{" +
                "status=" + status +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }


}
