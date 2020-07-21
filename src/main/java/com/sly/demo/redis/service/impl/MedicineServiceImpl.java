package com.sly.demo.redis.service.impl;

import com.sly.demo.redis.mapper.MedicineMapper;
import com.sly.demo.redis.model.Medicine;
import com.sly.demo.redis.service.MedicineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author SLY
 * @description TODO
 * @date 2020/7/20
 */
@Service
public class MedicineServiceImpl implements MedicineService {

    @Resource
    private MedicineMapper medicineMapper;

    @Override
    public List<Medicine> selectAllData() {
        return medicineMapper.selectAllData();
    }
}
