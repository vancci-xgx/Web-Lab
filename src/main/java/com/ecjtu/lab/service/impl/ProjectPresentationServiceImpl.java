package com.ecjtu.lab.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.ProjectPresentation;
import com.ecjtu.lab.mapper.ProjectPresentationMapper;
import com.ecjtu.lab.service.ProjectPresentationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Date;

@Service
public class ProjectPresentationServiceImpl implements ProjectPresentationService {
@Resource
    private ProjectPresentationMapper projectPresentationMapper;


    @Override
    public Page<ProjectPresentation> getProjectPresentationList(Long current, Long size, String searchParam) {
        Page<ProjectPresentation> page=new Page<>(current,size);
        if (StringUtils.isBlank(searchParam)) {
            return projectPresentationMapper.selectPage(page, new QueryWrapper<ProjectPresentation>().orderByDesc("create_time"));
        }
        return projectPresentationMapper.selectPage(page, new QueryWrapper<ProjectPresentation>()
                .like("name", searchParam)
                .or()
                .like("introduce", searchParam)
                .orderByDesc("create_time")
        );
    }


    @Override
    public Boolean insertProjectPresentation(String name, String content, String linkUrl) {
        ProjectPresentation projectPresentation = new ProjectPresentation();
        projectPresentation.setProjectName(name);
        projectPresentation.setIntroduce(content);
        projectPresentation.setCreateTime(new Date());
        projectPresentation.setLinkUrl(linkUrl);
        int result = projectPresentationMapper.insert(projectPresentation);
        return result == 1 ? true : false;
    }

    @Override
    public Boolean deleteProjectPresentation(Long id) {
        int result = projectPresentationMapper.deleteById(id);
        return result == 1 ? true : false;
    }

    @Override
    public ProjectPresentation getProjectPresentationById(Long id) {
        ProjectPresentation projectPresentation = projectPresentationMapper.selectById(id);
        return projectPresentation;
    }

    @Override
    public Boolean updateProjectPresentation(Long id, String name, String content, String linkUrl) {
        ProjectPresentation presentation = this.getProjectPresentationById(id);
        presentation.setLinkUrl(linkUrl);
        presentation.setIntroduce(content);
        presentation.setProjectName(name);
        int result = projectPresentationMapper.updateById(presentation);
        return result == 1 ? true : false;
    }
}
