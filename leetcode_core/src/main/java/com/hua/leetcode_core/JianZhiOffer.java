package com.hua.leetcode_core;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-12 10:15
 */

public class JianZhiOffer {

    public static void main(String[] args) {
        boolean exist = new JianZhiOffer().find(7, new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}});
        System.out.println("exist = " + exist);
    }

    /**
     * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
     * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
     */
    public boolean find(int target, int[][] array) {
        // 这是升级版的二分查找啊。
        // 基础知识：java中array.length拿到的是列数，array[0].length拿到的是行数，把array[0]看出数组就好理解了
        // 思路：遍历每一列，对该列二分查找target在不在
        // 陷阱：开始拿到此题还以为要对行和列分别做二分法，结果失算了，题目中说的是单独的某一行或者某一列是有序，
        // 但是如果结合起来就不保证是有序的了，比如下面这个矩阵：
        // {1, 2,  4,  6},
        // {2, 4,  7,  8},
        // {8, 9,  10, 11},
        // {9, 12, 13, 15}
        // 开始我的思路是，二分每一列，然后对比target与该列第一个值和最后一个值的大小，并且有如下逻辑：
        // 当target比最后一个值还大时，那么target一定在该列的右边；如果比第一个值还小的话，那么target一定在该列的左边。
        // 根据此逻辑，把target=7套进去看看，会发现第一次二分时(half=1)，target就处于该列的最大值和最小值之间，但是7并不在index
        // 等于1这一列（PS：事实上以上四列都满足上述逻辑）。思路出错的原因就是列与列之间并没有相对关系，后面一列的最小值不一定
        // 会比前面一列的最大值大。解决办法就是遍历每一列，分别二分法找target。
        int row = array.length;
        for (int i = 0; i < row; i++) {
            int start = 0;
            int end = array[i].length - 1;
            while (start <= end) {
                int half = start + (end - start) / 2;
                int value = array[i][half];
                if (target > value) {
                    start = half + 1;
                } else if (target < value) {
                    end = half - 1;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。
     */
    public String replaceSpace(StringBuffer str) {
        // 此题感觉有些无聊，不知道考点在哪。。。
        // 看了题解：
        // 1、StringBuffer本身就有length和charAt等方法，因此先toString没必要。
        String input = str.toString();
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == ' ') {
                builder.append("%20");
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }
}
