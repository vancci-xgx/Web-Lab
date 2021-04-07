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
 * 授课视频
 * by xgx
 */
@Data
@TableName("teaching_video")
public class TeachingVideo implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("title")
    private String title;//视频标题
    @TableField("teacher_id")
    private Long teacherId;
    @TableField("teacher_name")
    private String teacherName;
    @TableField("classify_id")//视频分类
    private Long classifyId;
    @TableField("video_classify")
    private String videoClassify;
    @TableField("video_iframe")
    private String videoIframe;  //视频和描述都放在这里 使用B站iframe
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime = new Date();//发布时间
}
