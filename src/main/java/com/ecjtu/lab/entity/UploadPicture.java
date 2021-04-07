package com.ecjtu.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("upload_picture")
public class UploadPicture implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("source")
    private String source;//来源 1 热点新闻 2公告栏 3导师介绍 4案例学习 5学生照片 6项目管理
    @TableField("pic_address")
    private String picAddr;//图片地址
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate=new Date();//上传时间
}
