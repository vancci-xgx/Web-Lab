package com.ecjtu.lab.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.BulletinBoard;
import com.ecjtu.lab.mapper.BulletinBoardMapper;
import com.ecjtu.lab.service.BulletinBoardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class BulletinBoardServiceImpl implements BulletinBoardService {
    @Resource
    BulletinBoardMapper bulletinBoardMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public Page<BulletinBoard> getBulletinBoardList(Long current, Long size, String searchParam) {
        Page<BulletinBoard> bulletinBoardPage = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return bulletinBoardMapper.selectPage(bulletinBoardPage, new QueryWrapper<BulletinBoard>().orderByDesc("create_time"));
        }
        return bulletinBoardMapper.selectPage(bulletinBoardPage, new QueryWrapper<BulletinBoard>()
                .like("title", searchParam)
                .or()
                .like("content", searchParam)
                .or()
                .like("author", searchParam)
                .orderByDesc("create_time")
        );
    }

    @Override
    public Boolean updateBulletinBoard(Long id,String title, String content) {
        BulletinBoard bulletinBoard = bulletinBoardMapper.selectById(id);
        if (bulletinBoard==null) return false;
        bulletinBoard.setTitle(title);
        bulletinBoard.setContent(content);
        int result = bulletinBoardMapper.updateById(bulletinBoard);
        redisUtil.del(CommonConstant.BULLETIN_BOARD_LIST,CommonConstant.BULLETIN_BOARD_TOP7);
        return result == 1 ? true : false;
    }

    @Override
    public Boolean insertBulletinBoard(String title, String content,String author) {
        BulletinBoard bulletinBoard = new BulletinBoard();
        bulletinBoard.setContent(content);
        bulletinBoard.setTitle(title);
        bulletinBoard.setAuthor(author);
        int result = bulletinBoardMapper.insert(bulletinBoard);
        redisUtil.del(CommonConstant.BULLETIN_BOARD_LIST,CommonConstant.BULLETIN_BOARD_TOP7);
        //删除缓存
        return result == 1 ? true : false;
    }

    @Override
    public Boolean delBulletinBoard(Long id) {
        int result = bulletinBoardMapper.deleteById(id);
        redisUtil.del(CommonConstant.BULLETIN_BOARD_LIST,CommonConstant.BULLETIN_BOARD_TOP7);
        return result == 1 ? true : false;
    }
     @Override
    public BulletinBoard getById(Long id){
        BulletinBoard bulletinBoard = bulletinBoardMapper.selectById(id);
        return bulletinBoard;
    }

    /**
     * 前台展示 查找前7  前台查询先放入Redis（管理系统添加时删除key）
     * @return
     */
    @Override
    public List<BulletinBoard> getBulletinBoardsOnFront() {
        boolean hasKey=redisUtil.hasKey(CommonConstant.BULLETIN_BOARD_TOP7);
        if (hasKey){
            return (List<BulletinBoard>)redisUtil.get(CommonConstant.BULLETIN_BOARD_TOP7);
        }else {
            List<BulletinBoard> bulletinBoardsOnFront = bulletinBoardMapper.getBulletinBoardsOnFront();
            redisUtil.set(CommonConstant.BULLETIN_BOARD_TOP7,bulletinBoardsOnFront);
            return bulletinBoardsOnFront;
        }
    }
    /**
     * 前台页面查看公告
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public BulletinBoard getByIdFront(Long id) {
        BulletinBoard bulletinBoard = bulletinBoardMapper.selectById(id);
        if (bulletinBoard==null)return null;
        Integer pageView=bulletinBoard.getPageView();
        pageView += 1;
        bulletinBoard.setPageView(pageView);
        bulletinBoardMapper.updateById(bulletinBoard);
        return bulletinBoard;
    }
    /**
     * 前台页面查看所有公告
     */
    @Override
    public Page<BulletinBoard> getAllBulletinBoardListFront(Long current, Long size){
        boolean hasKey = redisUtil.hHasKey(CommonConstant.BULLETIN_BOARD_LIST,String.valueOf(current));
        if (hasKey){
            return (Page<BulletinBoard>)redisUtil.hget(CommonConstant.BULLETIN_BOARD_LIST,String.valueOf(current));
        }else {
            Page<BulletinBoard> bulletinBoardList = this.getBulletinBoardList(current, size, null);
            redisUtil.hset(CommonConstant.BULLETIN_BOARD_LIST, String.valueOf(current),bulletinBoardList);
            return bulletinBoardList;
        }
    }
}
