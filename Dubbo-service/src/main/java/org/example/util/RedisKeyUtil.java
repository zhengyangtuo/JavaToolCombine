package org.example.util;

public class RedisKeyUtil {
    private static final String SPLIT=":";
    private static final String PREFIX_UV="uv";
    private static final String PREFIX_DAU="dau";
    private static final String PREFIX_POST = "post";
    private static final String PREFIX_ENTITY_LIKE = "post:entity";

    /**
     * 单日uv
     * **/
    public static String getUVKey(String date){
        return PREFIX_UV + SPLIT + date;
    }
    /**
     * 区间UV
     * **/
    public static String getUVKey(String startDate,String endDate){
        return PREFIX_UV + SPLIT + startDate + SPLIT + endDate;
    }
    /**
     * 单日dau
     * **/
    public static String getDAUKey(String date){
        return PREFIX_DAU + SPLIT + date;
    }
    /***
     * 区间UV
     * */
    public static String getDAUKey(String startDate,String endDate){
        return PREFIX_DAU + SPLIT + startDate + SPLIT + endDate;
    }

    public static String getPostScoreKey() {
        return PREFIX_POST + SPLIT + "score";
    }

    public static String getEntityLikeKey(int entityTypePost, int discussPostId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityTypePost + SPLIT + discussPostId;
    }
}
