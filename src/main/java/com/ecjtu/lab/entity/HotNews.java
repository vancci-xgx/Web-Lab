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
 * 热点新闻
 * by xgx
 */
@Data
@TableName("hot_news")
public class HotNews implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("title")
    private String title;//公告标题
    @TableField("page_view")
    private Integer pageView = 0;//浏览量 默认为0
    @TableField("content")
    private String content;//内容
    @TableField("author")
    private String author;//作者
    @TableField("status")
    private String status="0";//0 显示 1取消显示
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime = new Date();//发布时间
}
