package com.hua.leetcode_core.classic_algorithm;

//
// Created by clydeazhang on 2022/5/7 2:54 下午.
// Copyright (c) 2022 Tencent. All rights reserved.
// 
class KMP {
    public static void main(String[] args) {
//        int[] next = buildNext("abababac");
//        StringBuilder builder = new StringBuilder();
//        builder.append("[");
//        for (int i : next) {
//            builder.append(i).append(",");
//        }
//        builder.append("]");
//        System.out.println("next=" + builder.toString());
        int index = kmpSearch("aaaaa", "bba");
        System.out.println("index="+index);
    }

    public static int kmpSearch(String s, String p) {
        int[] next = buildNext(p);
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }
            if (s.charAt(i) == p.charAt(j)) {
                j++;
            }
            if (j == p.length()) {
                return i - j + 1;
            }
        }
        return -1;
    }

    /**
     * 构造字符串p的next数组, 数组中存储的就是p[0,i]子串的最大重叠字符的个数。
     */
    private static int[] buildNext(String p) {
        if (p == null || p.length() == 0) {
            return null;
        }
        int[] next = new int[p.length()];
        int j = 0;
        next[0] = 0;
        for (int i = 1; i < p.length(); i++) {
            while (j > 0 && p.charAt(i) != p.charAt(j)) {
                j = next[j - 1];
            }
            if (p.charAt(i) == p.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }
}
