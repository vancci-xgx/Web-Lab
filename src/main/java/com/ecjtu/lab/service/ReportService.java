package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.ProjectPresentation;
import com.ecjtu.lab.entity.Report;

import java.util.Date;

public interface ReportService {

    Page<Report> getReportList(Long current, Long size, String searchParam);
    Boolean addReport(String presenterName, String presentSummary, String linkUrl, Date reportDate);
    Boolean deleteReportById(Long id);
    Report getReportById(Long id);
    Boolean updateReport(Long id,String presenterName,String presentSummary,String linkUrl,Date reportDate);
}
