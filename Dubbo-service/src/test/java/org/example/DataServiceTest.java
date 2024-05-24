package org.example;

import org.example.service.DataService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.swing.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=ProviderApplication.class)
public class DataServiceTest {
    @Autowired
    private DataService dataService;
    @Resource
    private RedisTemplate<String,Object> redisTemplate;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    @Test
    public void setAndGetUVTest() throws ParseException {
        flushRedis();
        dataService.recordUv("197.168.1.1");
        dataService.recordUv("197.168.1.2");
        dataService.recordUv("197.168.1.3");
        Date cur = new Date();
        long count = dataService.caculateUv(cur,cur);
        Assert.assertEquals("单日ip统计失败",count,3);
        //重复ip
        dataService.recordUv("197.168.1.3");
        count = dataService.caculateUv(cur,cur);
        Assert.assertEquals("单日重复ip统计失败",count,3);
        //计算时间内
        Date lastDay = simpleDateFormat.parse("2024-05-22");
        dataService.recordUv(lastDay,"197.168.1.4");
        count = dataService.caculateUv(lastDay,new Date());
        Assert.assertEquals("范围时间内ip统计失败",count,4);

        dataService.recordUv(lastDay,"197.168.1.3");
        count = dataService.caculateUv(lastDay,new Date());
        Assert.assertEquals("范围时间内ip统计失败",count,4);
    }
    @Test
    public void setAndGetDauTest() throws ParseException {
        flushRedis();
        dataService.recordDau(1);
        dataService.recordDau(2);
        dataService.recordDau(3);
        Date cur = new Date();
        long count = dataService.caculateDau(cur,cur);
        Assert.assertEquals("单日活统计失败",3,count);
        //单日客户号重新登陆
        dataService.recordDau(3);
        count = dataService.caculateDau(cur,cur);
        Assert.assertEquals("单日日活重复统计失败",3,count);
        //计算时间内
        Date lastDay = simpleDateFormat.parse("2024-05-22");
        dataService.recordDau(lastDay,4);
        count = dataService.caculateDau(lastDay,new Date());
        Assert.assertEquals("间隔时间日活统计失败",4,count);

        dataService.recordDau(lastDay,3);
        count = dataService.caculateDau(lastDay,new Date());
        Assert.assertEquals("间隔时间日活重复统计失败",4,count);
    }
    @Test
    public void test3(){
        Object o = redisTemplate.opsForValue().get("1");
        System.out.println(o);
    }
    private void flushRedis(){
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.flushDb();
                return null;
            }
        });
    }
    public static void main(String[] args){
//        byte[][] cur = new byte[][]{{1,2,3},{1,2,3},{1,2,3}};
//        test2(cur);
        String cur = "123";
        byte[] bytes = cur.getBytes();
        System.out.println(Arrays.toString(bytes));
    }
    private static void test2(byte[] ... args){
        for (byte[] bytes : args){
            System.out.println(bytes);
        }
    }
}