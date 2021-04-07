package com.ecjtu.lab.tools;

import com.ecjtu.lab.entity.ResponseMsg;
import com.ecjtu.lab.enums.ResponseEnum;

public class ResponseUtil {
    public static ResponseMsg getSuccessRes() {
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), null);
    }
    public static ResponseMsg getRes() {
        return new ResponseMsg();
    }
    public static ResponseMsg getErrorRes() {
        return new ResponseMsg(ResponseEnum.ERROR.getCode(),ResponseEnum.ERROR.getMsg(),null);
    }
    public static ResponseMsg getErrorRes(String msg) {
        return new ResponseMsg(ResponseEnum.ERROR.getCode(),msg,null);
    }

    public static ResponseMsg getSuccessMsg(String msg,Object data) {
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(),msg,null);
    }
    public static ResponseMsg getUserAddErrorRes() {
        return new ResponseMsg(ResponseEnum.USER_ADD_ERROR.getCode(),ResponseEnum.USER_ADD_ERROR.getMsg(),null);
    }

    public static ResponseMsg getSuccessRes(Object data) {
        return new ResponseMsg(ResponseEnum.SUCCESS.getCode(), ResponseEnum.SUCCESS.getMsg(), data);
    }
}
