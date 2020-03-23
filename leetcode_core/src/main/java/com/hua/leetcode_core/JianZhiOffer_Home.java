package com.hua.leetcode_core;

/**
 * @author hua
 * @version V1.0
 * @date 2020/3/23 21:34
 */
public class JianZhiOffer_Home {

    /**
     * 变态跳台阶。
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     */
    public int JumpFloorII(int n) {
        // n级版本的动态规划呗
        // dp(n) = dp(n-1)+dp(n-2)+...+dp(1)
        // 题解后记：这个题目青蛙可以跳n级台阶，属于特殊情况，用dp思想虽然能解，
        // 但是存在嵌套遍历所以不是最优的，题解有个一步到位的思路：
        // 每个台阶都有跳与不跳两种情况（除了最后一个台阶），最后一个台阶必须跳，
        // 所以一共的跳法是: 2^(n-1)，使用移位的方式来实现的话就是：
        // return 1<<(n-1);
        int[] dpArray = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int sum = 1;
            for (int j = 0; j < i; j++) {
                sum += dpArray[j];
            }
            dpArray[i] = sum;
        }
        return dpArray[n];
        // return 1<<(n-1);
    }

}
