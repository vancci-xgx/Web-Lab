package com.ecjtu.lab.exception.handler;

import com.ecjtu.lab.entity.ResponseMsg;
import com.ecjtu.lab.enums.ResponseEnum;
import com.ecjtu.lab.exception.TokenException;
import com.ecjtu.lab.tools.ResponseUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(TokenException.class)
    public ResponseMsg handleTokenException(TokenException tokenException) {
        ResponseMsg res = ResponseUtil.getRes();
        res.setCode(ResponseEnum.AUTH_DENIED.getCode());
        res.setMsg(ResponseEnum.AUTH_DENIED.getMsg());
        return res;
    }
}
