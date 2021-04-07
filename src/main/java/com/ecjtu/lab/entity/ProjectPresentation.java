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
 * 项目展示
 */
@TableName("project_presentation")
@Data
public class ProjectPresentation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("project_name")
    private String projectName;
    @TableField("introduce")
    private String introduce;
    @TableField("link_url")
    private String linkUrl;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime=new Date();
}
