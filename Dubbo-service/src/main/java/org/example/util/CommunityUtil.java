package org.example.util;

import java.util.UUID;

public class CommunityUtil {
    /***
     * 生成随机字符串
     * */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
