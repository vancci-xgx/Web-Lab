package com.ecjtu.lab.entity;

import com.ecjtu.lab.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMsg implements Serializable {
    private String code;//响应代码
    private String msg;//响应信息
    private Object data;//响应数据


}
