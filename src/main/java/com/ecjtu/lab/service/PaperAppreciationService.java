package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.PaperAppreciation;

import java.util.Date;


public interface PaperAppreciationService {
    Page<PaperAppreciation> getPaperAppreciationList(Long current, Long size, String searchParam);
    Boolean insertPaperAppreciation(String title, String journalName, Date publicationTime, String linkUrl);
    Boolean deletePaperAppreciation(Long id);
    PaperAppreciation getPaperAppreciationById(Long id);
}
