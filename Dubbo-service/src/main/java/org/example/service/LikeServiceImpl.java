package org.example.service;

import org.example.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Override
    public Long findEntityLikeCount(int entityTypePost, int discussPostId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityTypePost,discussPostId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    //1代表点赞，0代表未点赞
    @Override
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId) ? 1 : 0;
    }
}
