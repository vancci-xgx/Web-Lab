package com.ecjtu.lab.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.PaperAppreciation;
import com.ecjtu.lab.entity.VideoClassify;
import com.ecjtu.lab.mapper.VideoClassifyMapper;
import com.ecjtu.lab.service.VideoClassifyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VideoClassifyServiceImpl implements VideoClassifyService {
    @Resource
    private VideoClassifyMapper videoClassifyMapper;

    @Override
    public boolean addVideoClassify(String name) {
        VideoClassify videoClassify = new VideoClassify();
        videoClassify.setClassifyName(name);
        int result = videoClassifyMapper.insert(videoClassify);
        return result == 1 ? true : false;
    }

    @Override
    public boolean delVideoClassify(Long id) {
        int result = videoClassifyMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public Page<VideoClassify> selectVideoClassifyList(Long current, Long size, String searchParam) {
        Page<VideoClassify> page=new Page<>(current,size);
        if (StringUtils.isBlank(searchParam)) {
            return videoClassifyMapper.selectPage(page, new QueryWrapper<>());
        }
        return videoClassifyMapper.selectPage(page, new QueryWrapper<VideoClassify>()
                .like("classify_name", searchParam)
                .orderByDesc("create_time")
        );
    }

    @Override
    public List<VideoClassify> getAllVideoClassify() {
        return videoClassifyMapper.selectList(new QueryWrapper<VideoClassify>());
    }
}
