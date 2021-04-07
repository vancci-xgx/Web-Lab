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
@TableName("teacher_ppt")
public class TeacherPPT implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("teacher_id")
    private Long teacherId;
    @TableField("teacher_name")
    private String teacherName;
    @TableField("link_url")
    private String linkUrl;
    @TableField("course_name")
    private String courseName;
    @TableField("course_describe")
    private String courseDescribe;
    @TableField("ppt_name")
    private String pptName;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate=new Date();
}
