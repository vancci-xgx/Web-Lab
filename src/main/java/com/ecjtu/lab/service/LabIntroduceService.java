package com.ecjtu.lab.service;

import com.ecjtu.lab.entity.LabIntroduce;

public interface LabIntroduceService {
    void updateIntroduce(String introduce);
    LabIntroduce select();
}
