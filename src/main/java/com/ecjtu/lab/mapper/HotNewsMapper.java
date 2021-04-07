package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.HotNews;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HotNewsMapper extends BaseMapper<HotNews> {

    @Select("select * FROM hot_news ORDER BY create_time DESC LIMIT 0,5")
     List<HotNews> getHotNewsOnFront();
}
