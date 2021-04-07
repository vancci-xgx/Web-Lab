package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.TeacherIntroduce;
import com.ecjtu.lab.entity.dto.TeacherDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeacherIntroduceMapper extends BaseMapper<TeacherIntroduce> {

    @Select("select t.id, t.name from teacher_introduce t ")
     List<TeacherDTO> getTeacherNameList();

}
