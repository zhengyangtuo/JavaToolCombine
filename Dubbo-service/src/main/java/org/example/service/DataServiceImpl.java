package org.example.service;

import org.example.constant.ReturnCode;
import org.example.exception.BussinessException;
import org.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Resource(name = "redisT")
    private RedisTemplate<String,Object> redisTemplate;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public long caculateUv(Date start, Date end) {
        if (start == null || end  == null)
        {
            throw new BussinessException("参数不能为空",ReturnCode.FAIL);
        }
        if (start.after(end)){
            throw  new BussinessException("起始天数不能大于结束天数",ReturnCode.FAIL);
        }
        String redisKey = RedisKeyUtil.getUVKey(df.format(start),df.format(end));
        //判断是否之前计算过，如果不包含今天，其实可以复用
        Date cur = saveDay(new Date());
        if (cur.after(end)){
            Object o = redisTemplate.opsForValue().get(redisKey);
            if (o != null){
                return redisTemplate.opsForHyperLogLog().size(redisKey);
            }
        }
        List<String> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)){
            String key = RedisKeyUtil.getUVKey(df.format(calendar.getTime()));
            keyList.add(key);
            calendar.add(Calendar.DATE,1);
        }
        return redisTemplate.opsForHyperLogLog().union(redisKey,keyList.toArray(new String[0]));
//        Long count = redisTemplate.opsForHyperLogLog().size(redisKey);
//        return count == null ? 0 : count;
    }

    @Override
    public long caculateDau(Date start, Date end) {
        if (start == null || end == null){
            throw new BussinessException("参数不能为空",ReturnCode.FAIL);
        }
        if (start.after(end)){
            throw new BussinessException("起始日不能晚于结束日",ReturnCode.FAIL);
        }
        String redisKey = RedisKeyUtil.getDAUKey(df.format(start),df.format(end));
        //判断是否之前计算过，如果不包含今天，其实可以复用
        Date cur = saveDay(new Date());
        if (cur.after(end)){
            Object o = redisTemplate.opsForValue().get(redisKey);
            if (o != null){
                return redisTemplate.opsForValue().size(redisKey);
            }
        }
        List<byte[]> keyList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(start);
        while (!calendar.getTime().after(end)){
            String key = RedisKeyUtil.getDAUKey(df.format(calendar.getTime()));
            keyList.add(key.getBytes());
            calendar.add(Calendar.DATE,1);
        }
        byte[] bytes = RedisKeyUtil.getDAUKey(df.format(new Date())).getBytes();
        return (long)redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                System.out.println(redisKey);
                redisConnection.bitOp(RedisStringCommands.BitOperation.OR,
                        redisKey.getBytes(),keyList.toArray(new byte[0][0]));
                return redisConnection.bitCount(redisKey.getBytes());
            }
        });
    }

    @Override
    public void recordUv(String ip) {
        String key = RedisKeyUtil.getUVKey(df.format(new Date()));
        redisTemplate.opsForHyperLogLog().add(key,ip);
    }

    @Override
    public void recordDau(int userId) {
        String key = RedisKeyUtil.getDAUKey(df.format(new Date()));
        redisTemplate.opsForValue().setBit(key,userId,true);
    }

    @Override
    public void recordUv(Date date,String ip) {
        String key = RedisKeyUtil.getUVKey(df.format(date));
        redisTemplate.opsForHyperLogLog().add(key,ip);
    }
    @Override
    public void recordDau(Date date,int userId) {
        String key = RedisKeyUtil.getDAUKey(df.format(date));
        redisTemplate.opsForValue().setBit(key,userId,true);
    }
    /**
     * 只保留日期中的年月日，其他均为0
     * **/
    private static Date saveDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        return calendar.getTime();
    }
    public static void main(String[] args){
//        Date date = new Date();
//        date = saveDay(date);
//        System.out.println(date);
        String path = System.getProperty("user.dir");
        System.out.println(path);
    }
}
