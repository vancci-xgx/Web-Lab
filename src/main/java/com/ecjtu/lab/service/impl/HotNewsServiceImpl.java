package com.ecjtu.lab.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.HotNews;
import com.ecjtu.lab.mapper.HotNewsMapper;
import com.ecjtu.lab.service.HotNewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.List;
@Service
public class HotNewsServiceImpl implements HotNewsService {

    @Resource
    HotNewsMapper hotNewsMapper;
    @Resource
    RedisUtil redisUtil;

    @Override
    public Page<HotNews> getHotNewsList(Long current, Long size, String searchParam) {
        Page<HotNews> hotNewsPage = new Page<>(current, size);
        if (StringUtils.isBlank(searchParam)) {
            return hotNewsMapper.selectPage(hotNewsPage, new QueryWrapper<HotNews>().orderByDesc("create_time"));
        }
        return hotNewsMapper.selectPage(hotNewsPage, new QueryWrapper<HotNews>()
                .like("title", searchParam)
                .or()
                .like("content", searchParam)
                .or()
                .like("author", searchParam)
                .orderByDesc("create_time")
        );
    }

    @Override
    public boolean insertHotNews(String title, String content, String author) {
        HotNews hotNews = new HotNews();
        hotNews.setTitle(title);
        hotNews.setContent(content);
        hotNews.setAuthor(author);
        int result = hotNewsMapper.insert(hotNews);
        //清缓存
        redisUtil.del(CommonConstant.HOT_NEW_TOP5,CommonConstant.HOT_NEW_LIST);
        return result == 1 ? true : false;
    }

    @Override
    @Transactional
    public boolean updateHotNews(Long id, String title, String content) {
        HotNews hotNews = hotNewsMapper.selectById(id);
        if (hotNews == null) return false;
        hotNews.setContent(content);
        hotNews.setTitle(title);
        int result = hotNewsMapper.updateById(hotNews);
        redisUtil.del(CommonConstant.HOT_NEW_TOP5,CommonConstant.HOT_NEW_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public boolean delHotNew(Long id) {
        int result = hotNewsMapper.deleteById(id);
        redisUtil.del(CommonConstant.HOT_NEW_TOP5,CommonConstant.HOT_NEW_LIST);
        return result == 1 ? true : false;
    }

    @Override
    public HotNews getHotNewById(Long id) {
        return hotNewsMapper.selectById(id);
    }

    /**
     * 前台查询先放入Redis（管理系统添加时删除key） 按时间顺序倒序查找前7个
     *
     * @return
     */
    @Override
    public List<HotNews> getHotNewsOnFront() {
        boolean hasKey=redisUtil.hasKey(CommonConstant.HOT_NEW_TOP5);
        if (hasKey){
            return (List<HotNews>)redisUtil.get(CommonConstant.HOT_NEW_TOP5);
        }else {
            List<HotNews> HotNewList = hotNewsMapper.getHotNewsOnFront();
            redisUtil.set(CommonConstant.HOT_NEW_TOP5,HotNewList);
            return HotNewList;
        }
    }

    /**
     * 前台页面查看新闻
     *
     * @param id
     * @return
     */
    @Override
    @Transactional
    public HotNews getHotNewByIdFront(Long id) {
        HotNews hotNew = hotNewsMapper.selectById(id);
        if (hotNew==null) return null;
        Integer pageView=hotNew.getPageView();
        pageView+=1;
        hotNew.setPageView(pageView);
        hotNewsMapper.updateById(hotNew);
        return hotNew;
    }
    /**
     * 前台页面查看所有新闻
     */
    @Override
  public Page<HotNews> getHotNewsListFront(Long current, Long size){
      boolean hasKey=redisUtil.hHasKey(CommonConstant.HOT_NEW_LIST,String.valueOf(current));
      if (hasKey){
          return (Page<HotNews>)redisUtil.hget(CommonConstant.HOT_NEW_LIST,String.valueOf(current));
      }else{
          Page<HotNews> page = this.getHotNewsList(current   ,size   ,null );
          redisUtil.hset(CommonConstant.HOT_NEW_LIST, String.valueOf(current),page);
          return page;
      }
  }
}
