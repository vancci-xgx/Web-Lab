package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.TeacherIntroduce;
import com.ecjtu.lab.entity.dto.TeacherDTO;

import java.util.List;

public interface TeacherIntroduceService {
    Page<TeacherIntroduce> getTeacherIntroduceList(Long current, Long size, String searchParam);

    boolean insertTeacherIntroduce(String teacherName, String content, String teacherPic);

    boolean delTeacherIntroduce(Long id);

    boolean updateTeacherIntroduce(Long id, String name, String picAddress, String introduce);

    TeacherIntroduce  getTeacherIntroduceById(Long id);
    TeacherIntroduce  frontGetTeacherIntroduceById(Long id);
    List<TeacherIntroduce> getTeacherIntroduceList();
    List<TeacherDTO> getTeacherDTOList();
}
