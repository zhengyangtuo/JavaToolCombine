package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pojo.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    int insertComment(Comment comment);

    List<Comment> selectCommunityByEntity(@Param("entityType") int entityType,@Param("entityId") int entityId,@Param("offset") int offset,@Param("limit") int limit);

    int findCommentCount(@Param("entityType")int entityType,@Param("entityId") int entityId);

    Comment findCommentById(int entityId);

}
