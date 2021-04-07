package com.ecjtu.lab.tools;


import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UploadUtil {

public static String generateAddr(MultipartFile file){
    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
    String format = simpleDateFormat.format(new Date());
    String uuid = UUID.randomUUID().toString().replace("-", "");
    String addr = format + "/" + uuid+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    return addr;
}


/**
 * 判断文件是否是图片
 */
public static boolean isImage(MultipartFile file) {
    if (file.isEmpty()) {
        return false;
    }
    BufferedImage image = null;
    try {
        //如果是spring中MultipartFile类型，则代码如下

        image = ImageIO.read(file.getInputStream());
        if (image == null || image.getWidth() <= 0 || image.getHeight() <= 0) {
            return false;
        }
        return true;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
}
