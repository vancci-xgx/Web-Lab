package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ecjtu.lab.entity.TeachingVideo;

public interface TeachingVideoService {
    Page<TeachingVideo> getTeachingVideoList(Long current, Long size, String searchParam);
    boolean insertTeachingVideo(Long teacherId,String teacherName,String videoIframe,String title,Long classifyId,String classify);
    boolean delTeachingVideo(Long id);
    TeachingVideo getTeachingVideoById(Long id);
}
