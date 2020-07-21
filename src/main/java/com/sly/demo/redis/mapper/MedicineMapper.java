package com.sly.demo.redis.mapper;

import com.sly.demo.redis.model.Medicine;

import java.util.List;

/**
 * 药物mapper
 *
 * @author sly
 */
public interface MedicineMapper {
    /**
     * 查询全部数据
     * @return
     */
    List<Medicine> selectAllData();
}
