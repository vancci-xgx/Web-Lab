package com.ecjtu.lab.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ecjtu.lab.entity.BulletinBoard;

import java.util.List;

public interface BulletinBoardService {

    Page<BulletinBoard> getBulletinBoardList(Long current, Long size, String searchParam);
    Boolean insertBulletinBoard(String title,String content,String author);
    Boolean updateBulletinBoard(Long id,String title,String content);
    Boolean delBulletinBoard(Long id);
    BulletinBoard getById(Long id);
    List<BulletinBoard> getBulletinBoardsOnFront();
    BulletinBoard getByIdFront(Long id);
    Page<BulletinBoard> getAllBulletinBoardListFront(Long current, Long size);
}
