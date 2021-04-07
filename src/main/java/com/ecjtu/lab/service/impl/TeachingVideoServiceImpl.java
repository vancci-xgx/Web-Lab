package com.ecjtu.lab.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.TeachingVideo;
import com.ecjtu.lab.mapper.TeachingVideoMapper;
import com.ecjtu.lab.service.TeachingVideoService;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeachingVideoServiceImpl implements TeachingVideoService {
    @Resource
    TeachingVideoMapper teachingVideoMapper;

    @Override
    public Page<TeachingVideo> getTeachingVideoList(Long current, Long size, String searchParam) {
        Page<TeachingVideo> page = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return teachingVideoMapper.selectPage(page, new QueryWrapper<TeachingVideo>().orderByDesc("create_time"));
        }
        return teachingVideoMapper.selectPage(page, new QueryWrapper<TeachingVideo>()
                .like("teacher_name", searchParam)
                .or()
                .like("video_describe", searchParam)
                .or()
                .like("classify", searchParam)
                .orderByDesc("create_time")
        );
    }



    @Override
    public boolean insertTeachingVideo( Long teacherId, String teacherName,
                                       String videoIframe, String title, Long classifyId, String classify){
        TeachingVideo teachingVideo = new TeachingVideo();
        videoIframe = generateIframe(videoIframe);
        teachingVideo.setVideoIframe(videoIframe);
        teachingVideo.setTeacherId(teacherId);
        teachingVideo.setTeacherName(teacherName);
        teachingVideo.setClassifyId(classifyId);
        teachingVideo.setVideoClassify(classify);
        teachingVideo.setTitle(title);
        int result = teachingVideoMapper.insert(teachingVideo);
        return result == 1 ? true : false;
    }


    @Override
    public boolean delTeachingVideo(Long id) {
        int result = teachingVideoMapper.deleteById(id);
        return result == 1 ? true : false;
    }



    @Override
    public TeachingVideo getTeachingVideoById(Long id) {
        return teachingVideoMapper.selectById(id);
    }

    private String generateIframe(String videoIframe){
        Document parse = Jsoup.parse(videoIframe);
        Elements iframe = parse.getElementsByTag("iframe");
        String src = iframe.attr("src");
        src+="&as_wide=1&high_quality=1&danmaku=0";
        iframe.removeAttr(src);
        iframe.attr("src",src);
        iframe.attr("sandbox", "allow-top-navigation allow-same-origin allow-forms allow-scripts");
        iframe.attr("style", "width:100%;height:900px");
        return iframe.toString();
    }

}
