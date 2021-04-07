package com.ecjtu.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 导师介绍
 * by xgx
 */
@Data
@TableName("teacher_introduce")
public class TeacherIntroduce implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("name")
    private String name;//导师姓名
    @TableField("pic_address")
    private String picAddress;//导师照片地址
    @TableField("introduce")
    private String introduce;//导师介绍
    @TableField("page_view")
    private Integer pageView = 0;//浏览量
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime = new Date();//发布时间

}
