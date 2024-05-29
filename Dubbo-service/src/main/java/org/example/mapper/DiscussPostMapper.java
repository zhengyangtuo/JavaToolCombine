package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.DiscussPost;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    public List<DiscussPost> selectDiscussPosts(@Param("userId")int userId, @Param("offset")int offset, @Param("limit") int limit, @Param("orderMode")int orderMode);

    public int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int entityId);

    int updateScore(@Param("id")int id, @Param("score") double score);

    void updateCommentCount(@Param("entityId")int entityId, @Param("count")int count);
}
