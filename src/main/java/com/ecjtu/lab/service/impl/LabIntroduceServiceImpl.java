package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.entity.LabIntroduce;
import com.ecjtu.lab.mapper.LabIntroduceMapper;
import com.ecjtu.lab.service.LabIntroduceService;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LabIntroduceServiceImpl implements LabIntroduceService {
    @Resource
    LabIntroduceMapper labIntroduceMapper;

    @Resource
    RedisUtil redisUtil;


    @Override
    public void updateIntroduce(String introduce) {
        redisUtil.del("labIntroduce");
        //该表只有一条数据
        LabIntroduce labIntroduce = labIntroduceMapper.selectOne(new QueryWrapper<LabIntroduce>().eq("id", "1"));
        if (labIntroduce == null) {
            labIntroduceMapper.insert( introduce);
        } else {
            labIntroduce.setIntroduce(introduce);
            labIntroduceMapper.update(labIntroduce, new UpdateWrapper<>());
        }
    }

    @Override
    public LabIntroduce select() {
        boolean flag = redisUtil.hasKey("labIntroduce");
        LabIntroduce labIntroduce=null;
        if (flag){
            labIntroduce=  (LabIntroduce)redisUtil.get("labIntroduce");
        }else {
            labIntroduce=labIntroduceMapper.selectOne(new QueryWrapper<LabIntroduce>().eq("id", "1"));
            redisUtil.set("labIntroduce", labIntroduce);
        }
        return labIntroduce;
    }
}
