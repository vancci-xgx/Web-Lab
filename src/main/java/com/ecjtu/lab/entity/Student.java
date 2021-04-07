package com.ecjtu.lab.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

/**
 * 学生 by xgx
 */
@Data
@TableName("student")
public class Student implements Serializable {
 @TableId(type = IdType.AUTO)
 private Long id;
 @TableField("name")
 private String name;//姓名
 @TableField("account")
 private String account;//账号
 @TableField("password")
 private String passWord;//密码
 @TableField("create_time")
 Date createTime=new Date();//账户创建时间
}
