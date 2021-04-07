package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.HotNews;

import java.util.List;

public interface HotNewsService {
    Page<HotNews> getHotNewsList(Long current, Long size, String searchParam);
    boolean insertHotNews(String title,String content,String author);
    boolean updateHotNews(Long id,String title,String content);
    boolean delHotNew(Long id);
    HotNews getHotNewById(Long id);
    HotNews getHotNewByIdFront(Long id);
    List<HotNews> getHotNewsOnFront();
    Page<HotNews> getHotNewsListFront(Long current, Long size);
}
