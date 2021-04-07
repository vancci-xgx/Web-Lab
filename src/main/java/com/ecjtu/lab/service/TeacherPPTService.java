package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.TeacherPPT;

public interface TeacherPPTService {
    Page<TeacherPPT> getTeacherPPTList(Long current, Long size, String searchParam);
    Boolean insertTeacherPPT(Long teacherId,String linkUrl,String teacherName,String pptName,String describe,String courseName);
    Boolean deleteTeacherPPT(Long id);
    Page<TeacherPPT> frontGetTeacherPPTList(Long current, Long size, Long teacherID);

}
