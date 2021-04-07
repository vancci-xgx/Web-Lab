package com.ecjtu.lab.service.impl;

import com.ecjtu.lab.entity.UploadPicture;
import com.ecjtu.lab.mapper.UploadPictureMapper;
import com.ecjtu.lab.service.UploadPictureService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UploadPictureServiceImpl implements UploadPictureService {

    @Resource
    UploadPictureMapper uploadPictureMapper;

    @Override
    public boolean insertUploadPic(String source, String addr) {
        UploadPicture uploadPicture = new UploadPicture();
        uploadPicture.setPicAddr(addr);
        uploadPicture.setSource(source);
        int result = uploadPictureMapper.insert(uploadPicture);
        return result == 1 ? true : false;
    }
}
