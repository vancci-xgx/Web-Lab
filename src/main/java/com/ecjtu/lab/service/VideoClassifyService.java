package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.VideoClassify;

import java.util.List;

public interface VideoClassifyService {

    boolean addVideoClassify(String name);
    boolean delVideoClassify(Long id);
    Page<VideoClassify> selectVideoClassifyList(Long current, Long size, String searchParam);
    List<VideoClassify> getAllVideoClassify();
}
