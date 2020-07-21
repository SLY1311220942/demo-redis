package com.sly.demo.redis.service;

import com.sly.demo.redis.model.Medicine;

import java.util.List;

/**
 * @author SLY
 * @description
 * @date 2020/7/20
 */
public interface MedicineService {
    /**
     * 查询全部数据
     * @return
     */
    List<Medicine> selectAllData();
}
