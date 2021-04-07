package com.ecjtu.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("video_classify")
public class VideoClassify {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("classify_name")
    private String classifyName;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    Date createTime = new Date();
}
