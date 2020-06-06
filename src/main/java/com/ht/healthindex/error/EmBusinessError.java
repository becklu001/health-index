package com.ht.healthindex.error;

/*
*   定义错误信息的枚举类
* */
public enum  EmBusinessError implements CommonError {
    /*
    *   预设的错误信息
    *   1 开头为系统错误
    * */
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKOWN_ERROR(10002,"未知错误"),

    /*
    *   2 开头的为用户相关错误
    * */
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户不存在或者密码错误"),
    USER_NOT_LOGIN(20003,"用户还未登录"),

    // 3 开头为设备相关信息
    DEVICE_NOT_EXIST(30001,"设备不存在");

    private int errCode;
    private String errMsg;

    private EmBusinessError(){ }

    private EmBusinessError(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
