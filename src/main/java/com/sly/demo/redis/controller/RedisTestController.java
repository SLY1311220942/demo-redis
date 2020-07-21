package com.sly.demo.redis.controller;

import com.alibaba.fastjson.JSON;
import com.sly.demo.redis.model.Medicine;
import com.sly.demo.redis.service.MedicineService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @author SLY
 * @description TODO
 * @date 2020/7/20
 */
@Controller
@RequestMapping("/redis")
public class RedisTestController {


    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private MedicineService medicineService;


    /**
     * 加载数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/loadData")
    public Map<String, Object> loadData() {
        Map<String, Object> result = new HashMap<>(16);
        try {
            String catchPre = "medicine:0:";

            List<Medicine> medicines = medicineService.selectAllData();
            // 批量保存数据
            redisTemplate.executePipelined((RedisCallback) connection -> {
                medicines.forEach(medicine -> {
                    String key = medicine.getManufacturer() + "_" + medicine.getDrugName();
                    redisTemplate.opsForValue().set(catchPre + key, JSON.toJSONString(medicine));
                });
                return null;
            });
            result.put("status", "200");
            result.put("msg", "加载数据完成");
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询数据
     *
     * @param d 药物名称
     * @param m 厂商名称
     * @return
     */
    @ResponseBody
    @RequestMapping("/queryData")
    public Map<String, Object> queryData(String m, String d) {
        Map<String, Object> result = new HashMap<>(16);
        try {
            String catchPre = "medicine:0:";
            long start = System.currentTimeMillis();
            // 活得满足条件的key
            String key = "*" + (StringUtils.isNotBlank(m) ? m : "")
                    + "*_*" + (StringUtils.isNotBlank(d) ? d : "") + "*";
            Set<String> keys = redisTemplate.keys(catchPre + key);
            // 获取所有数据
            List<Medicine> medicines = new ArrayList<>();
            redisTemplate.executePipelined((RedisCallback) connection -> {
                keys.forEach(k -> {
                    String s = redisTemplate.opsForValue().get(k);
                    Medicine medicine = JSON.parseObject(s, Medicine.class);
                    medicines.add(medicine);
                });
                return null;
            });


            long end = System.currentTimeMillis();
            System.out.println("查询用时:" + (end - start) + " 数据:" + medicines.size());
            result.put("status", "200");
            result.put("msg", "加载数据完成");
            result.put("medicines", medicines);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Medicine medicine = new Medicine();
        String key = "*" + (StringUtils.isNotBlank(medicine.getManufacturer()) ? medicine.getManufacturer() : "")
                + "*_*" + (StringUtils.isNotBlank(medicine.getDrugName()) ? medicine.getDrugName() : "") + "*";
        System.out.println(key);
    }
}
