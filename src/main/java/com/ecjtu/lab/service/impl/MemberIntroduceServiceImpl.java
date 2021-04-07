package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.MemberIntroduce;
import com.ecjtu.lab.entity.PaperAppreciation;
import com.ecjtu.lab.entity.dto.MemberDTO;
import com.ecjtu.lab.mapper.MemberIntroduceMapper;
import com.ecjtu.lab.service.MemberIntroduceService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MemberIntroduceServiceImpl implements MemberIntroduceService {
    @Resource
    private MemberIntroduceMapper memberIntroduceMapper;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public boolean addMemberIntroduce(String name, String url, String introduce) {
        MemberIntroduce memberIntroduce = new MemberIntroduce();
        memberIntroduce.setName(name);
        memberIntroduce.setPicUrl(url);
        memberIntroduce.setIntroduce(introduce);
        int result = memberIntroduceMapper.insert(memberIntroduce);
        redisUtil.del(CommonConstant.FRONT_MEMBER_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public Page<MemberIntroduce> getMemberIntroduceList(Long current, Long size, String searchParam) {
        Page<MemberIntroduce> page = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return memberIntroduceMapper.selectPage(page, new QueryWrapper<>());
        }
        return memberIntroduceMapper.selectPage(page, new QueryWrapper<MemberIntroduce>()
                .like("name", searchParam)
        );
    }

    @Override
    public MemberIntroduce getMemberIntroduce(Long id) {
        return memberIntroduceMapper.selectById(id);
    }


    @Override
    public MemberIntroduce frontGetMemberIntroduce(Long id) {
        boolean hasKey = redisUtil.hHasKey(CommonConstant.FRONT_MEMBER_DETAIL, String.valueOf(id));
        MemberIntroduce memberIntroduce;
        if (hasKey) {
            memberIntroduce = (MemberIntroduce) redisUtil.hget(CommonConstant.FRONT_MEMBER_DETAIL, String.valueOf(id));
        } else {
            memberIntroduce = memberIntroduceMapper.selectById(id);
            redisUtil.hset(CommonConstant.FRONT_MEMBER_DETAIL, String.valueOf(id), memberIntroduce, CommonConstant.EXPIRE_TIME);
        }
        return memberIntroduce;
    }

    @Override
    public boolean delMemberIntroduce(Long id) {
        int result = memberIntroduceMapper.deleteById(id);
        redisUtil.hdel(CommonConstant.FRONT_MEMBER_DETAIL, String.valueOf(id));
        redisUtil.del(CommonConstant.FRONT_MEMBER_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public boolean updateMemberIntroduce(Long id, String name, String url, String introduce) {
        MemberIntroduce memberIntroduce = memberIntroduceMapper.selectById(id);
        if (memberIntroduce == null) return false;
        memberIntroduce.setIntroduce(introduce);
        memberIntroduce.setName(name);
        memberIntroduce.setPicUrl(url);
        int result = memberIntroduceMapper.updateById(memberIntroduce);
        redisUtil.hdel(CommonConstant.FRONT_MEMBER_DETAIL, String.valueOf(id));
        return result == 1 ? true : false;
    }

    @Override
    public List<MemberDTO> getMemberDTOList() {
        boolean hasKey = redisUtil.hasKey(CommonConstant.FRONT_MEMBER_LIST);
        List<MemberDTO> list;
        if (hasKey) {
            list = (List<MemberDTO>) redisUtil.get(CommonConstant.FRONT_MEMBER_LIST);
            return list;
        }
        list = memberIntroduceMapper.getMemberDTOList();
        redisUtil.set(CommonConstant.FRONT_MEMBER_LIST, list);
        return list;
    }
}
