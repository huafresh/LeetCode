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

    /**
     * 矩阵覆盖。
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * 链接：https://www.nowcoder.com/practice/72a5a919508a4251859fb2cfb987a0e6?tpId=13&tqId=11163&rp=1&ru=/ta/coding-interviews&qru=/ta/coding-interviews/question-ranking
     */
    public int RectCover(int target) {
        // 提示都说递归了，那就先用递归搞一搞吧
        // return rectCoverRecursive(target);
        // 递归会有栈溢出的问题，其实大部分递归应该都是可以用迭代法解决的
        return rectCoverLoop(target);
    }

    private int rectCoverLoop(int n) {
        int prePre = 0; // 保存f(n-2)
        int pre = 0; // 保存f(n-1)
        int result = 0; // 保存f(n)
        for (int i = 1; i <= n; i++) {
            prePre = pre;
            pre = result;
            if (i == 1) {
                result = 1;
            } else if (i == 2) {
                result = 2;
            } else {
                result = pre + prePre;
            }
        }
        return result;
    }

    private int rectCoverRecursive(int n) {
        if (n < 1) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        }
        // 最终完成覆盖只有两种情况：
        // 第一种是一个小矩形被竖着放到大矩形的末尾；
        // 第二种情况是两个小矩形被横着一起放到大矩形的末尾。
        // 由此可得递归公式：f(n) = f(n-1) + f(n-2);
        return rectCoverRecursive(n - 1) + rectCoverRecursive(n - 2);
    }
}
