package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.ProjectPresentation;
public interface ProjectPresentationService {
    Page<ProjectPresentation> getProjectPresentationList(Long current, Long size, String searchParam);
    Boolean insertProjectPresentation(String name, String content,String linkUrl);
    Boolean deleteProjectPresentation(Long id);
    ProjectPresentation getProjectPresentationById(Long id);
    Boolean updateProjectPresentation(Long id,String name, String content,String linkUrl);
}
