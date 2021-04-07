package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.MemberIntroduce;
import com.ecjtu.lab.entity.dto.MemberDTO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberIntroduceMapper extends BaseMapper<MemberIntroduce> {

    @Select("SELECT id,name from member_introduce")
    List<MemberDTO> getMemberDTOList();

}
