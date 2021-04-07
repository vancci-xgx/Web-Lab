package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.LabIntroduce;
import org.apache.ibatis.annotations.Insert;

public interface LabIntroduceMapper extends BaseMapper<LabIntroduce> {

    @Insert("insert into lab_introduce values('1',#{introduce})")
    int insert(String introduce);

}
