package org.example.service;

public interface LikeService {
    Long findEntityLikeCount(int entityTypePost, int discussPostId);

    int findEntityLikeStatus(int userId, int entityType, int entityId);
}
