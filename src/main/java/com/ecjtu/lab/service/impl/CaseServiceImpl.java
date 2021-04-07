package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.Case;
import com.ecjtu.lab.entity.dto.CaseDTO;
import com.ecjtu.lab.mapper.CaseMapper;
import com.ecjtu.lab.service.CaseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CaseServiceImpl implements CaseService {

    @Resource
    private CaseMapper caseMapper;

    @Override
    public Page<Case> getCaseList(Long current, Long size, String searchParam) {
        Page<Case> page = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return caseMapper.selectPage(page, new QueryWrapper<Case>() .orderByDesc("create_time"));
        }
        return caseMapper.selectPage(page, new QueryWrapper<Case>()
                .like("case_name", searchParam)
                .or()
                .like("introduce", searchParam)
                .or()
                .like("link_url", searchParam)
                .orderByDesc("create_time")
        );
    }

    @Override
    public Boolean insertCase(String caseName, String introduce, String linkUrl) {
        Case c = new Case();
        c.setCaseName(caseName);
        c.setIntroduce(introduce);
        c.setLinkUrl(linkUrl);
        int r = caseMapper.insert(c);
        redisUtil.del(CommonConstant.FRONT_CASE_LIST);
        return r == 1 ? true : false;
    }

    @Override
    public Boolean delCase(Long id) {
        int result = caseMapper.deleteById(id);
        redisUtil.hdel(CommonConstant.CASE_DETAIL, String.valueOf(id));
        redisUtil.del(CommonConstant.FRONT_CASE_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public Case getCaseById(Long id) {
       return caseMapper.selectById(id);
    }

    @Override
    public Boolean updateCase(Long id, String caseName, String introduce) {
        Case c = caseMapper.selectById(id);
        c.setCaseName(caseName);
        c.setIntroduce(introduce);
        int r = caseMapper.updateById(c);
        redisUtil.hdel(CommonConstant.CASE_DETAIL, String.valueOf(id));
        redisUtil.del(CommonConstant.FRONT_CASE_LIST);
        return r == 1 ? true : false;
    }

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<CaseDTO> frontGetCases() {
        List<CaseDTO> caseDTOS;
        if (redisUtil.hasKey(CommonConstant.FRONT_CASE_LIST)){
            caseDTOS =(List<CaseDTO> ) redisUtil.get(CommonConstant.FRONT_CASE_LIST);
        }
        caseDTOS = caseMapper.frontGetCases();
        redisUtil.set(CommonConstant.FRONT_CASE_LIST, caseDTOS);
        return caseDTOS;
    }

    @Override
    public Case frontGetCaseById(Long id) {
        boolean hasKey = redisUtil.hHasKey(CommonConstant.CASE_DETAIL, String.valueOf(id));
        if (hasKey)return (Case) redisUtil.hget(CommonConstant.CASE_DETAIL, String.valueOf(id));
        Case c = caseMapper.selectById(id);
        redisUtil.hset(CommonConstant.CASE_DETAIL,String.valueOf(id),c);
        return c;
    }
}
