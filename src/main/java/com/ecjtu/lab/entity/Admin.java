package com.ecjtu.lab.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 管理员
 * by xgx
 */
@Data
@TableName("admin")
public class Admin implements Serializable {
    @TableId(type=IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;//管理员姓名
    @TableField("account")
    private String account;//账号
    @TableField("password")
    private String passWord;//密码
    @TableField("create_time")
    private Date createDate=new Date();//创建时间
}
