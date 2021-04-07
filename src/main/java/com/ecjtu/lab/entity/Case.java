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
 * 案例
 */
@Data
@TableName("case_study")
public class Case implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("case_name")
    private String caseName;
    @TableField("introduce")
    private String introduce;
    @TableField("link_url")
    private String linkUrl;
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime=new Date();

}
