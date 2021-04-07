package com.ecjtu.lab.enums;

public enum UserAuthEnum {
    COMMON_USER("0"),ADMIN("1");
    private String code;

    UserAuthEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
