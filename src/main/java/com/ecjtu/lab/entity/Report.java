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
@TableName("report")
public class Report implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("presenter_name")
    private String presenterName;//报告人
    @TableField("presenter_summary")
    private String presentSummary;//报告摘要
    @TableField("link_url")
    private String linkUrl;//下载链接
    @TableField("report_date")
    @JsonFormat(pattern = "yyyy-MM")
    private Date reportDate;//报告日期
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM")
    private Date createTime=new Date();

}
