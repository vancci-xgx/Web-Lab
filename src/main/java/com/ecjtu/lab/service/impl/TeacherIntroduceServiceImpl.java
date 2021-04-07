package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.TeacherIntroduce;
import com.ecjtu.lab.entity.dto.TeacherDTO;
import com.ecjtu.lab.mapper.TeacherIntroduceMapper;
import com.ecjtu.lab.service.TeacherIntroduceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherIntroduceServiceImpl implements TeacherIntroduceService {
    @Resource
    TeacherIntroduceMapper teacherIntroduceMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public Page<TeacherIntroduce> getTeacherIntroduceList(Long current, Long size, String searchParam) {
        Page<TeacherIntroduce> page = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return teacherIntroduceMapper.selectPage(page, new QueryWrapper<>());
        }
        return teacherIntroduceMapper.selectPage(page, new QueryWrapper<TeacherIntroduce>()
                .like("name", searchParam)
                .or()
                .like("introduce", searchParam));
    }

    @Override
    public boolean insertTeacherIntroduce(String teacherName, String content, String teacherPic) {
        TeacherIntroduce teacherIntroduce = new TeacherIntroduce();
        teacherIntroduce.setIntroduce(content);
        teacherIntroduce.setName(teacherName);
        teacherIntroduce.setPicAddress(teacherPic);
        int result = teacherIntroduceMapper.insert(teacherIntroduce);
        redisUtil.del(CommonConstant.FRONT_TEACHER_DTO_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public boolean delTeacherIntroduce(Long id) {
        int result = teacherIntroduceMapper.deleteById(id);
        redisUtil.del(CommonConstant.FRONT_TEACHER_DTO_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public boolean updateTeacherIntroduce(Long id, String name, String picAddress, String introduce) {
        TeacherIntroduce teacherIntroduce = teacherIntroduceMapper.selectById(id);
        if (teacherIntroduce == null) return false;
        teacherIntroduce.setName(name);
        teacherIntroduce.setPicAddress(picAddress);
        teacherIntroduce.setIntroduce(introduce);
        int result = teacherIntroduceMapper.updateById(teacherIntroduce);
        redisUtil.del(CommonConstant.FRONT_TEACHER_DTO_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public TeacherIntroduce getTeacherIntroduceById(Long id) {
        return teacherIntroduceMapper.selectById(id);
    }

    @Override
    public TeacherIntroduce frontGetTeacherIntroduceById(Long id) {
        TeacherIntroduce teacherIntroduce = teacherIntroduceMapper.selectById(id);
        Integer pageView = teacherIntroduce.getPageView();
        pageView += 1;
        teacherIntroduce.setPageView(pageView);
        teacherIntroduceMapper.updateById(teacherIntroduce);
        return teacherIntroduce;
    }

    @Override
    public List<TeacherIntroduce> getTeacherIntroduceList() {
        return teacherIntroduceMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public List<TeacherDTO> getTeacherDTOList() {
        boolean hasKey = redisUtil.hasKey(CommonConstant.FRONT_TEACHER_DTO_LIST);
        if (hasKey) return (List<TeacherDTO> )redisUtil.get(CommonConstant.FRONT_TEACHER_DTO_LIST);
        List<TeacherDTO> teacherNameList = teacherIntroduceMapper.getTeacherNameList();
        redisUtil.set(CommonConstant.FRONT_TEACHER_DTO_LIST,teacherNameList);
        return teacherNameList;
    }
}
