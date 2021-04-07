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
@TableName("paper_appreciation")
public class PaperAppreciation implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("title")
    private String title;//题目
    @TableField("journal_name")
    private String journalName;//会议名或者期刊名
    @TableField("link_url")
    private String linkUrl;//下载链接
    @TableField("publication_time")
    @JsonFormat(pattern = "yyyy-MM")
    private Date publicationTime;//发表时间
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime=new Date();//上传时间
}
