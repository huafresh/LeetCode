package com.hua.leetcode_core;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2019-08-05 12:00
 */

public class LeetCode_company {

    /**
     * <a href = "https://leetcode-cn.com/problems/length-of-last-word/">最后一个单词的长度</a>
     */
    public int lengthOfLastWord(String s) {
        // 此题看似简单，实则有些边界需要考虑
        // 1、字符串可能一个空格都没有
        // 2、最后可能包含若干空格
        int len = s.length();
        int count = 0;
        for(int i=len-1;i>=0;i--){
            if(s.charAt(i)!=' '){
                count++;
            } else{
                if(count!=0){
                    break;
                }
            }
        }
        return count;
    }

}
