package com.hua.leetcode_core;

public class LeetCodeMain {

    public static void main(String[] args) {

    }


    /**
     * 宝石与石头
     * https://leetcode-cn.com/problems/jewels-and-stones/
     */
    public int numJewelsInStones(String strJ, String strS) {
        int count = 0;
        for (int i = 0; i < strS.length(); i++) {
            if (strJ.indexOf(strS.charAt(i)) != -1) {
                count++;
            }
        }
        return count;
    }

}
