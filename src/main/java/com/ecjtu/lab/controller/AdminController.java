package com.ecjtu.lab.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.Annotation.PassToken;
import com.ecjtu.lab.cache.RedisUtil;
import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.*;
import com.ecjtu.lab.entity.dto.TeacherDTO;
import com.ecjtu.lab.enums.UserAuthEnum;
import com.ecjtu.lab.service.*;
import com.ecjtu.lab.tools.TokenUtil;
import com.ecjtu.lab.tools.ResponseUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 后台管理系统的接口都放在这
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    UserService userService;

    @Resource
    BulletinBoardService bulletinBoardService;
    @Resource
    HotNewsService hotNewsService;
    @Resource
    TeacherIntroduceService teacherIntroduceService;
    @Resource
    TeachingVideoService teachingVideoService;

    @Resource
    RedisUtil redisUtil;

    @PostMapping("/login")
    @PassToken
    public ResponseMsg adminLogin(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String passWord = request.getParameter("passWord");
        User user = userService.adminLogin(account, passWord);
        if (user != null) {
            String token = TokenUtil.makeToken();
            redisUtil.del("admin");
            redisUtil.hset("admin", token, user, TokenUtil.EXPIRE_TIME);
            redisUtil.hset("admin", "ip", request.getRemoteHost(), TokenUtil.EXPIRE_TIME);
            return ResponseUtil.getSuccessRes(token);
        }
        return ResponseUtil.getErrorRes("登陆失败！");
    }


    @GetMapping("/getUsers")
    public ResponseMsg getUsersByAuthCode(@RequestParam("code") String code,
                                          @RequestParam("current") Long current,
                                          @RequestParam("size") Long size,
                                          HttpServletRequest request
    ) {
        if (StringUtils.isBlank(code))
            return ResponseUtil.getErrorRes();
        String searchParam = request.getParameter("searchParam");
        Page<User> pageInfo = userService.findPageUsersByAuth(code, current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;

    }

    @GetMapping("/getBulletinBoardInfo")
    public ResponseMsg getBulletinBoardInfo(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                            HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<BulletinBoard> pageInfo = bulletinBoardService.getBulletinBoardList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;

    }

    @GetMapping("/getTeachingVideos")
    public ResponseMsg getTeachingVideos(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                         HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<TeachingVideo> pageInfo = teachingVideoService.getTeachingVideoList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @GetMapping("/getHotNewsInfo")
    public ResponseMsg getHotNewsInfo(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                      HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<HotNews> pageInfo = hotNewsService.getHotNewsList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @GetMapping("/getTeacherIntroduceInfo")
    public ResponseMsg getTeacherIntroduceInfo(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                               HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<TeacherIntroduce> pageInfo = teacherIntroduceService.getTeacherIntroduceList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    /**
     * 管理员添加用户
     *
     * @return
     */
    @PostMapping("/addUser")
    public ResponseMsg adminAddUser(HttpServletRequest request,
                                    @RequestParam("name") String name,
                                    @RequestParam("account") String account,
                                    @RequestParam("password") String password) {
        if (StringUtils.isBlank(name) || StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            return ResponseUtil.getErrorRes();
        }
        //这里 管理员添加的是普通用户
        int result = userService.addUser(name, account, password, UserAuthEnum.COMMON_USER.getCode());
        if (result == 0) {
            return ResponseUtil.getUserAddErrorRes();
        }
        return ResponseUtil.getSuccessRes();

    }

    @PostMapping("/addHotNews")
    public ResponseMsg addHotNews(HttpServletRequest request,
                                  @RequestParam("title") String title,
                                  @RequestParam("content") String content) {
        String token = request.getParameter("token");
        User admin = (User) redisUtil.hget("admin", token);
        boolean flag = hotNewsService.insertHotNews(title, content, admin.getName());
        if (flag) {
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes();
    }

    @PostMapping("/updateHotNews")
    public ResponseMsg updateHotNews(HttpServletRequest request,
                                     @RequestParam("id") Long id,
                                     @RequestParam("title") String title,
                                     @RequestParam("content") String content) {
        boolean flag = hotNewsService.updateHotNews(id, title, content);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/addBulletinBoard")
    public ResponseMsg addBulletinBoard(HttpServletRequest request,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content) {
        String token = request.getParameter("token");
        User admin = (User) redisUtil.hget("admin", token);
        Boolean flag = bulletinBoardService.insertBulletinBoard(title, content, admin.getName());
        if (flag) {
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes();
    }

    @PostMapping("/updateBulletinBoard")
    public ResponseMsg updateBulletinBoard(HttpServletRequest request,
                                           @RequestParam("title") String title,
                                           @RequestParam("content") String content,
                                           @RequestParam("id") Long id) {
        boolean flag = bulletinBoardService.updateBulletinBoard(id, title, content);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/delBulletinBoard")
    public ResponseMsg delBulletinBoard(@RequestParam("id") Long id) {
        boolean flag = bulletinBoardService.delBulletinBoard(id);
        if (flag) {
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes();
    }

    @PostMapping("/addTeacherIntroduce")
    public ResponseMsg addTeacherIntroduce(HttpServletRequest request,
                                           @RequestParam("teacherName") String teacherName,
                                           @RequestParam("content") String content,
                                           @RequestParam("teacherPic") String teacherPic) {
        boolean flag = teacherIntroduceService.insertTeacherIntroduce(teacherName, content, teacherPic);
        if (flag) {
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes();
    }

    @PostMapping("/delHotNew")
    public ResponseMsg delHotNew(HttpServletRequest request,
                                 @RequestParam("id") Long id) {
        boolean flag = hotNewsService.delHotNew(id);
        if (flag) {
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes();
    }

    @GetMapping("/getHotNew")
    public ResponseMsg getHotNew(HttpServletRequest request,
                                 @RequestParam("id") Long id) {
        HotNews hotNew = hotNewsService.getHotNewById(id);
        return ResponseUtil.getSuccessRes(hotNew);

    }

    @GetMapping("/getBulletinBoard")
    public ResponseMsg getBulletinBoard(HttpServletRequest request,
                                        @RequestParam("id") Long id) {
        BulletinBoard bulletinBoard = bulletinBoardService.getById(id);
        return ResponseUtil.getSuccessRes(bulletinBoard);

    }

    @PostMapping("/delTeacherIntroduce")
    public ResponseMsg delTeacherIntroduce(HttpServletRequest request,
                                           @RequestParam("id") Long id) {
        boolean flag = teacherIntroduceService.delTeacherIntroduce(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();

    }


    @GetMapping("/getTeacherIntroduce")
    public ResponseMsg getTeacherIntroduce(HttpServletRequest request,
                                           @RequestParam("id") Long id) {
        TeacherIntroduce teacherIntroduce = teacherIntroduceService.getTeacherIntroduceById(id);
        return ResponseUtil.getSuccessRes(teacherIntroduce);
    }

    @PostMapping("/updateTeacherIntroduce")
    public ResponseMsg updateTeacherIntroduce(HttpServletRequest request,
                                              @RequestParam("id") Long id,
                                              @RequestParam("content") String content,
                                              @RequestParam("teacherName") String teacherName,
                                              @RequestParam("teacherPic") String teacherPic) {
        boolean flag = teacherIntroduceService.updateTeacherIntroduce(id, teacherName, teacherPic, content);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }


    @PostMapping("/delUserAccount")
    public ResponseMsg delUserAccount(@RequestParam("id") Long id) {
        boolean flag = userService.delUserAccount(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/auditUserAccount")
    public ResponseMsg auditUserAccount(@RequestParam("id") Long id,
                                        @RequestParam("status") String status) {
        boolean flag = userService.auditUserAccount(id, status);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @GetMapping("/getUserInfo")
    public ResponseMsg getUserInfo(HttpServletRequest request, @RequestParam("id") Long id) {
        User user = userService.getUserById(id);
        return ResponseUtil.getSuccessRes(user);
    }

    @PostMapping("/updateUserInfo")
    public ResponseMsg updateUser(@RequestParam("id") Long id, @RequestParam("name") String name, @RequestParam("account") String account, @RequestParam("passWord") String passWord) {
        boolean flag = userService.updateUser(id, name, account, passWord);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @Resource
    TeacherPPTService teacherPPTService;

    @GetMapping("/getPPTsInfo")
    public ResponseMsg getPPTsInfo(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                   HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<TeacherPPT> pageInfo = teacherPPTService.getTeacherPPTList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @GetMapping("/getTeacherList")
    public ResponseMsg getTeacherList() {
        List<TeacherDTO> teacherDTOList = teacherIntroduceService.getTeacherDTOList();
        ResponseMsg res = ResponseUtil.getSuccessRes(teacherDTOList);
        return res;
    }

    @PostMapping("/addTeacherPPT")
    public ResponseMsg addTeacherPPT(@RequestParam("teacherId") Long id, @RequestParam("pptFile") MultipartFile file,@RequestParam("courseName")String courseName, @RequestParam("describe") String describe) throws Exception {
        if (file.isEmpty()) {
            return ResponseUtil.getErrorRes("没有上传ppt！");
        }
        String linkUrl = "/upload/ppt/" + file.getOriginalFilename();
        File ppt = new File(CommonConstant.UPLOAD_ADDR + "ppt/" + file.getOriginalFilename());
        if (!ppt.getParentFile().exists()) {
            ppt.getParentFile().mkdirs();
        }
        file.transferTo(ppt);
        TeacherIntroduce teacher = teacherIntroduceService.getTeacherIntroduceById(id);
        Boolean result = teacherPPTService.insertTeacherPPT(id, linkUrl, teacher.getName(), file.getOriginalFilename(), describe,courseName);
        if (!result) {
            return ResponseUtil.getErrorRes("发生错误,上传失败！");
        }
        return ResponseUtil.getSuccessRes("上传成功！");

    }

    @PostMapping("/deletePTT")
    public ResponseMsg deletePTT(@RequestParam("id") Long pptId) {
        Boolean flag = teacherPPTService.deleteTeacherPPT(pptId);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @Resource
    CaseService caseService;

    @GetMapping("/getCaseList")
    public ResponseMsg getCaseList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                   HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<Case> pageInfo = caseService.getCaseList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @GetMapping("/getCaseInfo")
    public ResponseMsg getCaseInfo(@RequestParam("id") Long id) {
        Case c = caseService.getCaseById(id);
        ResponseMsg res = ResponseUtil.getSuccessRes(c);
        return res;
    }

    @PostMapping("/delCase")
    public ResponseMsg delCase(@RequestParam("id") Long id) {
        Boolean flag = caseService.delCase(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/addCase")
    public ResponseMsg addCase(@RequestParam("caseName") String caseName,
                               @RequestParam("introduce") String introduce,
                               @RequestParam("file") MultipartFile file,
                               HttpServletRequest request) throws Exception {
        if (file.isEmpty()) {
            return ResponseUtil.getErrorRes("没有上传文件！");
        }
        String linkUrl = "/upload/case/" + file.getOriginalFilename();
        File ppt = new File(CommonConstant.UPLOAD_ADDR + "case/" + file.getOriginalFilename());
        if (!ppt.getParentFile().exists()) {
            ppt.getParentFile().mkdirs();
        }
        file.transferTo(ppt);
        Boolean flag = caseService.insertCase(caseName, introduce, linkUrl);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/updateCase")
    public ResponseMsg updateCase(@RequestParam("caseName") String caseName,
                                  @RequestParam("introduce") String introduce,
                                  @RequestParam("id") Long id,
                                  HttpServletRequest request
    ) {
        Boolean flag = caseService.updateCase(id, caseName, introduce);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @Resource
    PaperAppreciationService paperAppreciationService;

    @GetMapping("/getPaperAppreciationList")
    public ResponseMsg getPaperAppreciationList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                                HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<PaperAppreciation> pageInfo = paperAppreciationService.getPaperAppreciationList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @PostMapping("/addPaperAppreciation")
    public ResponseMsg addPaperAppreciation(HttpServletRequest request,
                                            @RequestParam("title") String title,
                                            @RequestParam("journalName") String journalName,
                                            @RequestParam("publicationTime") String publicationTime,
                                            @RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return ResponseUtil.getErrorRes("没有上传文件！");
        }
        String linkUrl = "/upload/paper/" + file.getOriginalFilename();
        File ppt = new File(CommonConstant.UPLOAD_ADDR + "paper/" + file.getOriginalFilename());
        if (!ppt.getParentFile().exists()) {
            ppt.getParentFile().mkdirs();
        }
        file.transferTo(ppt);
        Date date = DateUtils.parseDate(publicationTime, "yyyy-MM");
        Boolean flag = paperAppreciationService.insertPaperAppreciation(title, journalName, date, linkUrl);
        return flag ? ResponseUtil.getSuccessRes("上传成功！") : ResponseUtil.getErrorRes("发生错误,上传失败！");
    }

    @PostMapping("/delPaperAppreciation")
    public ResponseMsg delPaperAppreciation(@RequestParam("id") Long id) {
        Boolean flag = paperAppreciationService.deletePaperAppreciation(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @GetMapping("/getPaperAppreciationInfo")
    public ResponseMsg getPaperAppreciationInfo(@RequestParam("id") Long id) {
        PaperAppreciation appreciation = paperAppreciationService.getPaperAppreciationById(id);
        return ResponseUtil.getSuccessRes(appreciation);
    }

    @Resource
    LabIntroduceService labIntroduceService;

    @GetMapping("/getLabIntroduce")
    public ResponseMsg getLabIntroduce() {
        LabIntroduce labIntroduce = labIntroduceService.select();
        return ResponseUtil.getSuccessRes(labIntroduce);
    }

    @PostMapping("/updateLabIntroduce")
    public ResponseMsg updateLabIntroduce(HttpServletRequest request) {
        String introduce = request.getParameter("introduce");
        if (StringUtils.isNotBlank(introduce)) {
            labIntroduceService.updateIntroduce(introduce);
            return ResponseUtil.getSuccessRes();
        }
        return ResponseUtil.getErrorRes("实验室概括不能为空！");
    }

    @Resource
    MemberIntroduceService memberIntroduceService;

    @GetMapping("/getMemberIntroduceList")
    public ResponseMsg getMemberIntroduceList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                              HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<MemberIntroduce> pageInfo = memberIntroduceService.getMemberIntroduceList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }

    @GetMapping("/delMemberIntroduce")
    public ResponseMsg delMemberIntroduce(@RequestParam("id") Long id,
                                          HttpServletRequest request) {
        boolean flag = memberIntroduceService.delMemberIntroduce(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @GetMapping("/getMemberIntroduce")
    public ResponseMsg getMemberIntroduce(@RequestParam("id") Long id) {
        MemberIntroduce memberIntroduce = memberIntroduceService.getMemberIntroduce(id);
        return ResponseUtil.getSuccessRes(memberIntroduce);
    }

    @PostMapping("/addMemberIntroduce")
    public ResponseMsg addMemberIntroduce(@RequestParam("memberName") String name, @RequestParam("memberPic") String url, @RequestParam("content") String introduce) {
        boolean flag = memberIntroduceService.addMemberIntroduce(name, url, introduce);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/updateMemberIntroduce")
    public ResponseMsg updateMemberIntroduce(@RequestParam("id") Long id, @RequestParam("memberName") String name, @RequestParam("memberPic") String url, @RequestParam("content") String introduce) {
        boolean flag = memberIntroduceService.updateMemberIntroduce(id, name, url, introduce);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }


    @PostMapping("/addTeacherVideo")
    public ResponseMsg addTeachingVideo(
                                         @RequestParam("teacherId") Long teacherId,
                                         @RequestParam("teacherName") String teacherName,
                                         @RequestParam("classifyId")Long classifyId,
                                         @RequestParam("classifyName")String classifyName,
                                         @RequestParam("videoIframe")String videoIframe,
                                         @RequestParam("title")String title
                                         ) throws IOException {
        boolean flag = teachingVideoService.insertTeachingVideo(teacherId, teacherName, videoIframe, title, classifyId, classifyName);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @GetMapping("/delTeachingVideo")
    public ResponseMsg delTeachingVideo(@RequestParam("id") Long id) {
        boolean flag = teachingVideoService.delTeachingVideo(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }
    @Resource
    ProjectPresentationService projectPresentationService;

    @GetMapping("/getProjectPresentationList")
    public ResponseMsg getProjectPresentationList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                              HttpServletRequest request) {
        String searchParam = request.getParameter("searchParam");
        Page<ProjectPresentation> pageInfo = projectPresentationService.getProjectPresentationList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }
    @PostMapping("/addProjectPresentation")
    public ResponseMsg addProjectPresentation(HttpServletRequest request,@RequestParam("linkUrl")String url,
                                              @RequestParam("projectName")String projectName,@RequestParam("introduce")String introduce){
        Boolean flag = projectPresentationService.insertProjectPresentation(projectName, introduce, url);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/delProjectPresentation")
    public ResponseMsg delProjectPresentation(@RequestParam("id")Long id){
        Boolean flag = projectPresentationService.deleteProjectPresentation(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }
    @GetMapping("/getProjectPresentation")
    public ResponseMsg getProjectPresentation(@RequestParam("id")Long id){
        ProjectPresentation projectPresentation = projectPresentationService.getProjectPresentationById(id);
         return ResponseUtil.getSuccessRes(projectPresentation);
    }

    @PostMapping("/updateProjectPresentation")
    public ResponseMsg updateProjectPresentation(@RequestParam("id")Long id,@RequestParam("linkUrl")String url,
                                                 @RequestParam("projectName")String projectName,@RequestParam("introduce")String introduce){
        Boolean flag = projectPresentationService.updateProjectPresentation(id, projectName, introduce, url);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @Resource
    ReportService reportService;
    @GetMapping("/getReportList")
    public ResponseMsg getReportList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                     HttpServletRequest request){
        String searchParam = request.getParameter("searchParam");
        Page<Report> pageInfo = reportService.getReportList(current, size, searchParam);
        ResponseMsg res = ResponseUtil.getSuccessRes(pageInfo);
        return res;
    }
    @GetMapping("/delReport")
    public ResponseMsg delReport(@RequestParam("current") Long id,
                                     HttpServletRequest request){
        Boolean flag = reportService.deleteReportById(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/addReport")
    public ResponseMsg addReport(HttpServletRequest request,
                                 @RequestParam("file") MultipartFile file,
                                 @RequestParam("presenterName") String presenterName,
                                 @RequestParam("presentSummary") String presentSummary,
                                 @RequestParam("reportDateStr") String reportDateStr){
        if (file.isEmpty()) {
            return ResponseUtil.getErrorRes("没有上传文件！");
        }
        String linkUrl = "/upload/report/" + file.getOriginalFilename();
        File ppt = new File(CommonConstant.UPLOAD_ADDR + "report/" + file.getOriginalFilename());
        if (!ppt.getParentFile().exists()) {
            ppt.getParentFile().mkdirs();
        }
        try {
            file.transferTo(ppt);
            Date reportDate = DateUtils.parseDate(reportDateStr, "yyyy-MM-dd");
            Boolean flag = reportService.addReport(presenterName, presentSummary, linkUrl, reportDate);
            return flag ? ResponseUtil.getSuccessRes("上传成功！") : ResponseUtil.getErrorRes("发生错误,上传失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseUtil.getErrorRes("文件上传发生错误！");
        }

    }

    @Resource
    private VideoClassifyService videoClassifyService;
    @GetMapping("/getVideoClassifyList")
    public ResponseMsg getVideoClassifyList(@RequestParam("current") Long current, @RequestParam("size") Long size,
                                            HttpServletRequest request){
        String searchParam = request.getParameter("searchParam");
        Page<VideoClassify> page = videoClassifyService.selectVideoClassifyList(current, size, searchParam);
        return ResponseUtil.getSuccessRes(page);
    }

    @PostMapping("/delVideoClassify")
    public ResponseMsg delVideoClassify(@RequestParam("id")Long id){
        boolean flag = videoClassifyService.delVideoClassify(id);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @PostMapping("/addVideoClassify")
    public ResponseMsg addVideoClassify(@RequestParam("classifyName")String classifyName){
        boolean flag = videoClassifyService.addVideoClassify(classifyName);
        return flag ? ResponseUtil.getSuccessRes() : ResponseUtil.getErrorRes();
    }

    @GetMapping("/getAllVideoClassify")
    public ResponseMsg getAllVideoClassify(){
        List<VideoClassify> videoClassifyList = videoClassifyService.getAllVideoClassify();
        return ResponseUtil.getSuccessRes(videoClassifyList);
    }
}
