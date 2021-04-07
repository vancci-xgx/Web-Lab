package com.ecjtu.lab.enums;

public enum ResponseEnum {
    SUCCESS("200" , "success"),
    ERROR("201","error"),
    AUTH_DENIED("401","无权限,需要登录"),
    USER_ADD_ERROR("202","该用户名已经存在");
    private String code;
    private String msg;
    ResponseEnum(String code , String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
