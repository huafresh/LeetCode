package com.hua.leetcode_core;

//
// Created by clydeazhang on 2022/5/11 3:55 下午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

import java.util.Random;

/**
 * leetcode 学习专题
 */
public class Practice {

    public static void main(String[] args) {
        Practice practice = new Practice();
//        int[] nums = new int[]{4, 0, 0, 0, 0, 0};
//        int[] nums2 = new int[]{1, 2, 3, 5, 6};
//        practice.merge(nums, 1, nums2, 5);

        int[] prices = new int[]{7, 1, 3, 5, 4, 8};
        int profit = practice.maxProfit(prices);
    }

    /**
     * 删除排序数组中的重复项: https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2gy9m/
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 使用i, j快慢指针，可以减少移动次数
        int i = 0;
        int j = 1;
        while (i < nums.length && j < nums.length) {
            if (nums[i] != nums[j]) {
                nums[i + 1] = nums[j];
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i + 1;
    }

    /**
     * 合并两个有序的数组: https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnumcr/
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 两个数组都是升序排好序的，一开始就想到了双指针一直pk，然后把小点的值输出到数组
        // 为了不新分配内存空间，于是选择结果直接输出到nums1数组，但是碰到的问题是nums1数组前面的元素是有意义的。
        // 然后想到的是预处理把nums1数组前面的值拷贝到nums1数组后半段，然后再开始比较。
        // 后面看了题解真的是想抽自己耳光，为什么一定要pk选小值呢？从数组最后面开始往前遍历pk，
        // 选大的值输出到nums1数组后面不就行了吗。。。唉，思维受限啊。。。
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i];
                i--;
            } else {
                nums1[k--] = nums2[j];
                j--;
            }
        }
        while (i >= 0) {
            nums1[k--] = nums1[i--];
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }
    }

    /**
     * 打乱数组: https://leetcode.cn/leetbook/read/top-interview-questions-easy/xn6gq1/
     */
    public static class Solution {
        private final int[] nums;

        public Solution(int[] nums) {
            this.nums = nums;
        }

        public int[] reset() {
            if (nums == null) return null;
            return nums.clone();
        }

        public int[] shuffle() {
            if (nums == null) return null;
            int[] copy = nums.clone();
            Random random = new Random();
            for (int i = 0; i < copy.length; i++) {
                int off = random.nextInt(copy.length - i);
                int tmp = copy[i];
                copy[i] = copy[i + off];
                copy[i + off] = tmp;
            }
            return copy;
        }
    }

    /**
     * 买卖股票的最佳时机II: https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2zsx1/
     */
    public int maxProfit(int[] prices) {
        // 很惭愧，这一题不管是贪心法还是动态规划，自己都没有解出来。以下是评论区的思路：
        // 动态规划：
        // 用dp[n][0]表示第n天交易完后手里没有股票的最大利润；
        // 用dp[n][1]表示第n天交易完后手里有股票的最大利润。
        // 如果当天交易完手里没有股票，则分两种情况：
        // 第一种是前一天手里有股票，当天卖了，那么最大利润是：dp[n-1][1] + prices[n]
        // 第二种是前一天手里没有股票，当前无操作，那么最大利润等于dp[n-1][0]
        // 由此得到当天交易完手里没股票的状态转移方程：
        // dp[n][0] = Math.max(dp[n-1][1] + prices[n], dp[n-1][0])
        // 我们可以同理去分析当天交易完手里还有股票的情况：
        // dp[n][1] = Math.max(dp[n-1][0] - prices[n], dp[n-1][1])
        // PS: 对于上面dp[n][1]的状态转移公式我是持有疑问的：前一天持有股票，当天也没卖，那么最大利润不应该是dp[n-1][1]+prices[n]吗

//        if (prices.length == 0) return 0;
//        int hold = -prices[0];
//        int noHold = 0;
//        for (int i = 1; i < prices.length; i++) {
//            int newNoHold = Math.max(noHold, hold + prices[i]);
//            hold = Math.max(hold, noHold - prices[i]);
//            noHold = newNoHold;
//        }
//        return noHold;

        // 贪心算法：
        // 如果把每天的价格在坐标上画成折线，其实就是妥妥的k线图嘛，那么最大收益其实就是收获所有上涨，躲过所有下跌。
        // 因此我们可以遍历数组，通过对比今天和明天的价格确定明天会不会涨，会涨则今天买入，明天卖出（或者一直涨就一直持有）。
        int profit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i]) {
                profit += prices[i + 1] - prices[i];
            }
        }
        return profit;
    }
}
