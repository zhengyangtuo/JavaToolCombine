package org.example.service;

import org.example.pojo.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit, int orderMode);

    public int addDiscussPost(DiscussPost discussPost);

    DiscussPost findDiscussPostById(int entityId);

    int updateScore(int postId, double score);

    void updateCommentCount(int entityId, int count);
}
