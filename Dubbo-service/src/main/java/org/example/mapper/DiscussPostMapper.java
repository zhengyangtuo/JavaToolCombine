package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.DiscussPost;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscussPosts(@Param("userId")int userId, @Param("offset")int offset, @Param("limit") int limit, @Param("orderMode")int orderMode);
}
