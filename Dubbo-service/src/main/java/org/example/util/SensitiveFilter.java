package org.example.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.StringBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
public class SensitiveFilter {
    private static Logger logger = LoggerFactory.getLogger(SensitiveFilter.class);
    private static final String REPLACEMENT = "***";
    private TrieNode root = new TrieNode();
    @PostConstruct
    public void init(){
        try(
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                ) {
            String keyword;
            while ((keyword = reader.readLine()) != null){
                this.addKeyWord(keyword);
            }

        }catch (IOException e) {
            logger.error("加载敏感词文件失败"+e.getMessage());
        }
    }

    private void addKeyWord(String keyword) {
        TrieNode cur = root;
        for (int i = 0; i < keyword.length(); ++i)
        {
            char c = keyword.charAt(i);
            TrieNode  tmp = cur.getSubNode(c);
            if (tmp == null){
                cur.addSubNode(c,new TrieNode());
            }
            cur = cur.getSubNode(c);
            if (i == keyword.length() - 1){
                cur.setKeywordEnd(true);
            }
        }
    }
    /**
     * 过滤敏感词
     * **/
    public String filter(String text){
        if (StringUtils.isBlank(text)){
            return null;
        }

        TrieNode tempNode = root;
        int begin = 0;
        int end = 0;
        StringBuilder sb = new StringBuilder();
        while (end <  text.length()){
            char c = text.charAt(begin);
            if (isSymbol(c)){
                if (tempNode == root){
                    sb.append(c);
                    ++begin;
                }
                ++end;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null){
                sb.append(text.charAt(begin));
                end = ++begin;
                tempNode = root;
            }else if (tempNode.isKeywordEnd()){
                sb.append(REPLACEMENT);
                begin = ++end;
                tempNode = root;
            }else{
                ++end;
            }
        }
        sb.append(text.substring(begin));
        return sb.toString();
    }

    private boolean isSymbol(char c) {
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }
}
/**
 * 定义前缀树
 * */
class TrieNode{
    private boolean isKeywordEnd = false;
    private Map<Character,TrieNode> subNode = new HashMap<>();
    public boolean isKeywordEnd(){
        return isKeywordEnd;
    }
    public void setKeywordEnd(boolean keywordEnd){
        isKeywordEnd = keywordEnd;
    }
    public void addSubNode(Character c,TrieNode node){
        subNode.put(c,node);
    }
    public TrieNode getSubNode(Character c){
        return subNode.get(c);
    }
}
