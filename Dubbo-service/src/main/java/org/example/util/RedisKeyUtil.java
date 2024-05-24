package org.example.util;

public class RedisKeyUtil {
    private static final String SPLIT=":";
    private static final String PREFIX_UV="uv";
    private static final String PREFIX_DAU="dau";
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
}
