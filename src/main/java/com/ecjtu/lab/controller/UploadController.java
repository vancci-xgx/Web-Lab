package com.ecjtu.lab.controller;


import com.ecjtu.lab.constant.CommonConstant;
import com.ecjtu.lab.entity.ResponseMsg;
import com.ecjtu.lab.service.UploadPictureService;
import com.ecjtu.lab.tools.ResponseUtil;
import com.ecjtu.lab.tools.UploadUtil;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 上传文件接口 都是管理员添加,加上admin
 */
@RestController
@RequestMapping("/admin/upload")
@Log
public class UploadController {

    @Resource
    UploadPictureService uploadPictureService;

    @PostMapping("/addPic")
    public ResponseMsg uploadPic(@RequestParam("pic") MultipartFile multipartFile, HttpServletRequest request) throws FileNotFoundException {
        String source = request.getParameter("source");
        boolean isImage = UploadUtil.isImage(multipartFile);
        if (!isImage)  return ResponseUtil.getErrorRes("上传的不是图片");
        if (multipartFile.isEmpty()) {
            return ResponseUtil.getErrorRes("文件上传失败!");
        }
        if (StringUtils.isBlank(source)){
            return ResponseUtil.getErrorRes("图片没有来源,文件上传失败!");
        }

        String generateAddr = UploadUtil.generateAddr(multipartFile);
        String addr = CommonConstant.UPLOAD_ADDR+"pic/"+ generateAddr;
        File file = new File(addr);
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseUtil.getErrorRes("文件上传失败!");
        }
        String picAddr="/upload/pic/"+generateAddr;
        boolean flag = uploadPictureService.insertUploadPic(source, addr);
        if (!flag) return ResponseUtil.getErrorRes("文件上传失败!");
        return ResponseUtil.getSuccessRes(picAddr);
    }

}
