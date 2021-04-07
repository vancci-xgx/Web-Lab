package com.ecjtu.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ecjtu.lab.entity.BulletinBoard;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BulletinBoardMapper extends BaseMapper<BulletinBoard> {

    @Select("select * FROM bulletin_board ORDER BY create_time DESC LIMIT 0,7")
    List<BulletinBoard> getBulletinBoardsOnFront();
}
