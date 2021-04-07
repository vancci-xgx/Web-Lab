package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.Case;
import com.ecjtu.lab.entity.dto.CaseDTO;

import java.util.List;

public interface CaseService {
    Page<Case> getCaseList(Long current, Long size, String searchParam);
    Boolean insertCase(String caseName,String introduce,String linkUrl);
    Boolean delCase(Long id);
    Case getCaseById(Long id);
    Boolean updateCase(Long id,String caseName,String introduce);
    Case frontGetCaseById(Long id);
    List<CaseDTO> frontGetCases();
}
