package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.Case;
import com.ecjtu.lab.entity.dto.CaseDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CaseMapper extends BaseMapper<Case> {
    @Select("select c.id,c.case_name,c.create_time from case c")
    List<CaseDTO> frontGetCases();
}
