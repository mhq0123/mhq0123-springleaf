package com.mhq0123.springleaf.redis;

import com.mhq0123.springleaf.redis.service.RedisStringService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author mhq0123
 * @project mhq0123-springleaf
 * @fileName TestRedis
 * @date 2016-07-09
 * @memo
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring/ApplicationContext.xml")
public class TestRedis {

    @Autowired
    private RedisStringService redisStringService;

    @Test
    public void testSentinel() throws InterruptedException {
        int i=1;
        while(true) {
            try {
                redisStringService.set("test", "test_test"+(i++));
                System.out.println(redisStringService.get("test"));
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
