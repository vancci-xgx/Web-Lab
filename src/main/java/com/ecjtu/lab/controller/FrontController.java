package com.ecjtu.lab.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.Annotation.UserLoginToken;
import com.ecjtu.lab.entity.*;
import com.ecjtu.lab.entity.dto.MemberDTO;
import com.ecjtu.lab.entity.dto.TeacherDTO;
import com.ecjtu.lab.mapper.ProjectPresentationMapper;
import com.ecjtu.lab.service.*;
import com.ecjtu.lab.tools.ResponseUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 前台展示的接口都放这
 */
@RestController
@RequestMapping("/front")
public class FrontController {

    @Resource
    HotNewsService hotNewsService;
    @Resource
    BulletinBoardService bulletinBoardService;

    @GetMapping("/getHotNewsOnFront")
    public ResponseMsg getHotNewsOnFront() {
        List<HotNews> hotNewsOnFront = hotNewsService.getHotNewsOnFront();
        return ResponseUtil.getSuccessRes(hotNewsOnFront);
    }

    @GetMapping("/getBulletinBoardsOnFront")
    public ResponseMsg getBulletinBoardsOnFront() {
        List<BulletinBoard> bulletinBoardsOnFront = bulletinBoardService.getBulletinBoardsOnFront();
        return ResponseUtil.getSuccessRes(bulletinBoardsOnFront);
    }

    @GetMapping("/getHotNew")
    public ResponseMsg getHotNew(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return ResponseUtil.getErrorRes("查询错误");
        }
        HotNews hotNew = hotNewsService.getHotNewByIdFront(Long.valueOf(id));
        return ResponseUtil.getSuccessRes(hotNew);
    }

    @GetMapping("/getBulletinBoard")
    public ResponseMsg getBulletinBoard(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) {
            return ResponseUtil.getErrorRes("查询错误");
        }
        BulletinBoard bulletinBoard = bulletinBoardService.getByIdFront(Long.valueOf(id));
        return ResponseUtil.getSuccessRes(bulletinBoard);
    }

    @GetMapping("/getHotNewsList")
    public ResponseMsg getHotNewsList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) {
            return ResponseUtil.getErrorRes("缺少参数");
        }
        Page<HotNews> hotNewsPage = hotNewsService.getHotNewsListFront(Long.valueOf(current), 10L);
        return ResponseUtil.getSuccessRes(hotNewsPage);
    }

    @GetMapping("/getBulletinBoardList")
    public ResponseMsg getBulletinBoardList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) {
            return ResponseUtil.getErrorRes("缺少参数");
        }
        Page<BulletinBoard> bulletinBoardPage = bulletinBoardService.getAllBulletinBoardListFront(Long.valueOf(current), 10L);
        return ResponseUtil.getSuccessRes(bulletinBoardPage);
    }

    @Resource
    LabIntroduceService labIntroduceService;

    @Resource
    TeacherIntroduceService teacherIntroduceService;

    @GetMapping("/getLabIntroduce")
    public ResponseMsg getLabIntroduce() {
        LabIntroduce labIntroduce = labIntroduceService.select();
        return ResponseUtil.getSuccessRes(labIntroduce);
    }

    @GetMapping("/getTeacherIntroduceList")
    public ResponseMsg getTeacherIntroduceList() {
        List<TeacherIntroduce> teacherIntroduceList = teacherIntroduceService.getTeacherIntroduceList();
        return ResponseUtil.getSuccessRes(teacherIntroduceList);
    }

    @GetMapping("/getTeacherList")
    public ResponseMsg getTeacherList() {
        List<TeacherDTO> teachers = teacherIntroduceService.getTeacherDTOList();
        return ResponseUtil.getSuccessRes(teachers);
    }

    @GetMapping("/getTeacherIntroduce")
    public ResponseMsg getTeacherIntroduce(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) return ResponseUtil.getErrorRes("参数错误");
        TeacherIntroduce teacherIntroduce = teacherIntroduceService.frontGetTeacherIntroduceById(Long.valueOf(id));
        if (teacherIntroduce == null) {
            return ResponseUtil.getErrorRes("参数错误");
        }
        return ResponseUtil.getSuccessRes(teacherIntroduce);
    }

    @Resource
    TeacherPPTService teacherPPTService;

    @GetMapping("/getPPT")
    public ResponseMsg getPPT(HttpServletRequest request) {
        String teacherId = request.getParameter("teacherId");
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) {
            return ResponseUtil.getErrorRes("参数错误！");
        }

        Page<TeacherPPT> teacherPPTList = teacherPPTService.frontGetTeacherPPTList
                (Long.valueOf(current), 10l, StringUtils.isBlank(teacherId) ? null : Long.valueOf(teacherId));
        return ResponseUtil.getSuccessRes(teacherPPTList);
    }

    @Resource
    MemberIntroduceService memberIntroduceService;

    @GetMapping("/getMemberList")
    public ResponseMsg getMemberList() {
        List<MemberDTO> memberDTOList = memberIntroduceService.getMemberDTOList();
        return ResponseUtil.getSuccessRes(memberDTOList);
    }

    @GetMapping("/frontGetMemberIntroduce")
    public ResponseMsg frontGetMemberIntroduce(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) return ResponseUtil.getErrorRes("参数错误");
        MemberIntroduce memberIntroduce = memberIntroduceService.frontGetMemberIntroduce(Long.valueOf(id));
        if (memberIntroduce == null) return ResponseUtil.getErrorRes("参数错误");
        return ResponseUtil.getSuccessRes(memberIntroduce);
    }

    @Resource
    TeachingVideoService teachingVideoService;

    @GetMapping("/getTeachingVideoList")
    public ResponseMsg getTeachingVideoList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) return ResponseUtil.getErrorRes("参数错误!");
        Page<TeachingVideo> teachingVideoList = teachingVideoService.getTeachingVideoList(Long.valueOf(current), 15L, null);
        return ResponseUtil.getSuccessRes(teachingVideoList);
    }

    @GetMapping("/frontGetTeachingVideo")
    public ResponseMsg frontGetTeachingVideo(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) return ResponseUtil.getErrorRes("参数错误!");
        TeachingVideo teachingVideo = teachingVideoService.getTeachingVideoById(Long.valueOf(id));
        return ResponseUtil.getSuccessRes(teachingVideo);
    }

    @Resource
    ProjectPresentationService projectPresentationService;
    @GetMapping("/frontGetProject")
    public ResponseMsg frontGetProject(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) return ResponseUtil.getErrorRes("参数错误!");
        ProjectPresentation projectPresentation = projectPresentationService.getProjectPresentationById(Long.valueOf(id));
        if (projectPresentation == null) return ResponseUtil.getErrorRes("参数错误!");
        return ResponseUtil.getSuccessRes(projectPresentation);
    }

    @GetMapping("/getProjectList")
    public ResponseMsg getProjectList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) return ResponseUtil.getErrorRes("参数错误!");
        Page<ProjectPresentation> projectPresentationList = projectPresentationService.getProjectPresentationList(Long.valueOf(current), 10L, null);
        return ResponseUtil.getSuccessRes(projectPresentationList);
    }


    @Resource
    PaperAppreciationService paperAppreciationService;

    @GetMapping("/getPaperList")
    public ResponseMsg getPaperList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) return ResponseUtil.getErrorRes("参数错误!");
        Page<PaperAppreciation> paperAppreciationList = paperAppreciationService.getPaperAppreciationList(Long.valueOf(current), 10L, null);
        return ResponseUtil.getSuccessRes(paperAppreciationList);
    }

    @Resource
    ReportService reportService;

    @GetMapping("/getReportList")
    public ResponseMsg getReportList(HttpServletRequest request) {
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) return ResponseUtil.getErrorRes("参数错误!");
        Page<Report> page = reportService.getReportList(Long.valueOf(current), 10L, null);
        return ResponseUtil.getSuccessRes(page);
    }

    @GetMapping("/getReport")
    public ResponseMsg getReport(HttpServletRequest request) {
        String id = request.getParameter("id");
        if (StringUtils.isBlank(id)) return ResponseUtil.getErrorRes("参数错误!");
        Report report = reportService.getReportById(Long.valueOf(id));
        if (report == null) return ResponseUtil.getErrorRes("参数错误!");
        return ResponseUtil.getSuccessRes(report);
    }

    @Resource
    CaseService caseService;
    @GetMapping("/getCaseList")
    public ResponseMsg getCaseList(HttpServletRequest request){
        String current = request.getParameter("current");
        if (StringUtils.isBlank(current)) return ResponseUtil.getErrorRes("参数错误!");
        Page<Case> page = caseService.getCaseList(Long.valueOf(current), 10L, null);
        return ResponseUtil.getSuccessRes(page);
    }
    @GetMapping("/getCase")
    public ResponseMsg getCase(HttpServletRequest request){
        String id=request.getParameter("id");
        if (StringUtils.isBlank(id))return ResponseUtil.getErrorRes("参数错误!");
        Case c = caseService.getCaseById(Long.valueOf(id));
        if (c == null) return ResponseUtil.getErrorRes("参数错误!");
        return ResponseUtil.getSuccessRes(c);
    }


}