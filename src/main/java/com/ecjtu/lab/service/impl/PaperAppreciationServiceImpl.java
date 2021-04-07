package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.PaperAppreciation;
import com.ecjtu.lab.entity.TeacherPPT;
import com.ecjtu.lab.mapper.PaperAppreciationMapper;
import com.ecjtu.lab.service.PaperAppreciationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class PaperAppreciationServiceImpl implements PaperAppreciationService {
    @Resource
    private PaperAppreciationMapper paperAppreciationMapper;

    @Override
    public Page<PaperAppreciation> getPaperAppreciationList(Long current, Long size, String searchParam) {
        Page<PaperAppreciation> page=new Page<>(current,size);
        if (StringUtils.isBlank(searchParam)) {
            return paperAppreciationMapper.selectPage(page, new QueryWrapper<PaperAppreciation>().orderByDesc("create_time"));
        }
        return paperAppreciationMapper.selectPage(page, new QueryWrapper<PaperAppreciation>()
                .like("title", searchParam)
                .or()
                .like("journal_name", searchParam)
                .orderByDesc("create_time")
        );
    }

    @Override
    public Boolean insertPaperAppreciation(String title, String journalName, Date publicationTime, String linkUrl) {
        PaperAppreciation paperAppreciation = new PaperAppreciation();
        paperAppreciation.setTitle(title);
        paperAppreciation.setJournalName(journalName);
        paperAppreciation.setLinkUrl(linkUrl);
        paperAppreciation.setPublicationTime(publicationTime);
        int result = paperAppreciationMapper.insert(paperAppreciation);
        return result == 1 ? true : false;
    }

    @Override
    public Boolean deletePaperAppreciation(Long id) {
        int result = paperAppreciationMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public PaperAppreciation getPaperAppreciationById(Long id) {
        PaperAppreciation paperAppreciation = paperAppreciationMapper.selectById(id);
        return paperAppreciation;
    }
}
