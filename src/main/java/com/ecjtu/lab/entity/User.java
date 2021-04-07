package com.ecjtu.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ecjtu.lab.enums.UserAuthEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;//姓名
    @TableField("account")
    private String account;//账号
    @TableField("password")
    private String passWord;//密码
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date createTime = new Date();//账户创建时间
    @TableField("status")
    private String status;//账号状态 0注册待审核 1正常使用 2禁止使用
    @TableField("auth")
    private String Auth = UserAuthEnum.COMMON_USER.getCode(); //  0为普通用户 1管理员 默认为0
}
