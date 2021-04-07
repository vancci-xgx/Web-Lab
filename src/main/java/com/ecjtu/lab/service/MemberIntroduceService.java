package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.MemberIntroduce;
import com.ecjtu.lab.entity.dto.MemberDTO;

import java.util.List;

public interface MemberIntroduceService {
    boolean addMemberIntroduce(String name,String url,String introduce);
    Page<MemberIntroduce> getMemberIntroduceList(Long current, Long size, String searchParam);
    MemberIntroduce getMemberIntroduce(Long id);
    MemberIntroduce frontGetMemberIntroduce(Long id);
    boolean delMemberIntroduce(Long id);
    boolean updateMemberIntroduce(Long id,String name,String url,String introduce);
    List<MemberDTO> getMemberDTOList();

}
