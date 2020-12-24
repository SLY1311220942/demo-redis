package com.sly.demo.redis.lock;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author SLY
 * @description
 * @date 2020/6/16
 */
@Controller
@RequestMapping("/demo")
public class DemoLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 使用Redisson实现分布式锁
     *
     * @param request
     * @return
     * @author SLY
     */
    @ResponseBody
    @RequestMapping("/demoLock")
    public Map<String, Object> demoLock(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(16);
        RLock testLock = redissonClient.getLock("LOCK_KEY:asdas");
        try {
            testLock.lock();
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功：" + realStock);
                resultMap.put("status", 200);
                resultMap.put("message", "扣减成功");
            } else {
                System.out.println("扣减失败");
                resultMap.put("status", 400);
                resultMap.put("message", "扣减失败");
            }
            return resultMap;
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
            resultMap.put("status", 500);
            resultMap.put("message", "系统错误");
            return resultMap;
        } finally {
            testLock.unlock();
        }
    }

    /**
     * 使用redis实现
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/demoLock2")
    public Map<String, Object> demoLock2(HttpServletRequest request) {
        Map<String, Object> resultMap = new HashMap<>(16);
        String lockKey = "LOCK_KEY";
        String clientId = UUID.randomUUID().toString().replace("-", "");
        Timer timer = null;
        try {
            Boolean result = redisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 10, TimeUnit.SECONDS);
            if (!result) {
                resultMap.put("message", "系统繁忙");
                resultMap.put("status", 200);
                return resultMap;
            }

            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        redisTemplate.opsForValue().set(lockKey, clientId, 10, TimeUnit.SECONDS);
                    }
                }, 0, 3);

                int realStock = stock - 1;
                redisTemplate.opsForValue().set("stock", realStock + "");
                System.out.println("扣减成功：" + realStock);
                resultMap.put("status", 200);
                resultMap.put("message", "扣减成功");
            } else {
                System.out.println("扣减失败，库存不足！");
                resultMap.put("status", 400);
                resultMap.put("message", "扣减失败");
            }
            return resultMap;
        } catch (Exception e) {
            System.out.println(ExceptionUtils.getStackTrace(e));
            resultMap.put("status", 500);
            resultMap.put("message", "系统错误");
            return resultMap;
        } finally {
            if (timer != null) {
                timer.cancel();
            }
            if (clientId.equals(redisTemplate.opsForValue().get(lockKey))) {
                redisTemplate.delete(lockKey);
            }
        }
    }
}
