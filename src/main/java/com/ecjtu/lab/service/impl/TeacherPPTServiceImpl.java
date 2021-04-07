package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.TeacherIntroduce;
import com.ecjtu.lab.entity.TeacherPPT;
import com.ecjtu.lab.mapper.TeacherPPTMapper;
import com.ecjtu.lab.service.TeacherPPTService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TeacherPPTServiceImpl implements TeacherPPTService {
    @Resource
    TeacherPPTMapper teacherPPTMapper;

    @Override
    public Page<TeacherPPT> frontGetTeacherPPTList(Long current, Long size, Long teacherID) {
        Page<TeacherPPT> page = new Page<>(current,size);
        if (teacherID==null){
            return teacherPPTMapper.selectPage(page, new QueryWrapper<TeacherPPT>().orderByDesc("create_time"));
        }
        return teacherPPTMapper.selectPage(page, new QueryWrapper<TeacherPPT>().eq("teacher_id",teacherID).orderByDesc("create_time"));
    }

    @Override
    public Page<TeacherPPT> getTeacherPPTList(Long current, Long size, String searchParam) {
        Page<TeacherPPT> page = new Page<>(current,size);
        if (StringUtils.isBlank(searchParam)) {
            return teacherPPTMapper.selectPage(page, new QueryWrapper<>());
        }
        return teacherPPTMapper.selectPage(page, new QueryWrapper<TeacherPPT>()
                .like("teacher_name", searchParam)
                .or()
                .like("ppt_name", searchParam)
                .or()
                .like("course_describe",searchParam)
                .or()
                .like("course_name",searchParam)
        );

    }

    @Override
    public Boolean insertTeacherPPT(Long teacherId, String linkUrl, String teacherName, String pptName,String describe,String courseName) {
        TeacherPPT teacherPPT = new TeacherPPT();
        teacherPPT.setLinkUrl(linkUrl);
        teacherPPT.setPptName(pptName);
        teacherPPT.setTeacherId(teacherId);
        teacherPPT.setTeacherName(teacherName);
        teacherPPT.setCourseDescribe(describe);
        teacherPPT.setCourseName(courseName);
        int result = teacherPPTMapper.insert(teacherPPT);
        return result == 1 ? true : false;
    }

    @Override
    public Boolean deleteTeacherPPT(Long id) {
        int result = teacherPPTMapper.deleteById(id);
        return result == 1 ? true : false;
    }
}
