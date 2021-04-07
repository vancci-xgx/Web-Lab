package com.ecjtu.lab.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.Report;
import com.ecjtu.lab.mapper.ReportMapper;
import com.ecjtu.lab.service.ReportService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class ReportServiceImpl implements ReportService {

    @Resource
    ReportMapper reportMapper;

    @Override
    public Page<Report> getReportList(Long current, Long size, String searchParam) {
        Page<Report> page=new Page<>(current,size);
        if (StringUtils.isBlank(searchParam)) {
            return reportMapper.selectPage(page, new QueryWrapper<Report>().orderByDesc("create_time"));
        }
        return reportMapper.selectPage(page, new QueryWrapper<Report>()
                .like("presenter_name", searchParam)
                .or()
                .like("presenter_summary", searchParam).orderByDesc("create_time")
        );
    }


    @Override
    public Boolean addReport(String presenterName, String presentSummary, String linkUrl, Date reportDate) {
        Report report = new Report();
        report.setLinkUrl(linkUrl);
        report.setPresenterName(presenterName);
        report.setPresentSummary(presentSummary);
        report.setReportDate(reportDate);
        int result = reportMapper.insert(report);
        return result == 1 ? true : false;
    }

    @Override
    public Boolean deleteReportById(Long id) {
        int result = reportMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public Report getReportById(Long id) {
        return reportMapper.selectById(id);
    }

    @Override
    public Boolean updateReport(Long id,String presenterName,String presentSummary,String linkUrl,Date reportDate) {
        Report report = this.getReportById(id);
        report.setLinkUrl(linkUrl);
        report.setPresenterName(presenterName);
        report.setPresentSummary(presentSummary);
        report.setReportDate(reportDate);
        int result = reportMapper.updateById(report);
        return result == 1 ? true : false;
    }
}
