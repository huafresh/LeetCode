package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.Stack;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-12 10:15
 */

public class JianZhiOffer {

    public static void main(String[] args) {
        boolean exist = new JianZhiOffer().find(7, new int[][]{{1, 2, 8, 9}, {2, 4, 9, 12}, {4, 7, 10, 13}, {6, 8, 11, 15}});
        System.out.println("exist = " + exist);

        int[] pre = new int[]{1, 2, 4, 7, 3, 5, 6, 8};
        int[] in = new int[]{4, 7, 2, 1, 5, 3, 8, 6};
        TreeNode root = rebuildBinaryTree(pre, in);

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

    /**
     * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
     */
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        // 两个方法：
        // 1、省时间：用一个辅助栈
        // 2、省空间：用list的add(0, value)方法，这个方法会移动数组，目测是更耗时的吧～～
        // 看完题解还有个思路不错：即递归，递归的话要考虑栈溢出的问题，除此以外比1，2方法更优。
        final ArrayList<Integer> resultList = new ArrayList<>();
        ListNode curNode = listNode;
        while (curNode != null) {
            resultList.add(0, curNode.val);
            curNode = curNode.next;
        }
        return resultList;
    }

    private static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode rebuildBinaryTree(int[] pre, int[] in) {
        if (pre.length == 0 || in.length == 0) {
            return null;
        }
        return rebuildBinaryTreeRecursive(pre, 0, pre.length - 1, in, 0, in.length - 1);
    }

    private static TreeNode rebuildBinaryTreeRecursive(int[] pre, int preStart, int preEnd,
                                                       int[] in, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }
        int rootVal = pre[preStart];
        TreeNode root = new TreeNode(rootVal);
        for (int i = inStart; i <= inEnd; i++) {
            if (in[i] == rootVal) {
                root.left = rebuildBinaryTreeRecursive(pre, preStart + 1, preStart + i - inStart,
                        in, inStart, i - 1);
                root.right = rebuildBinaryTreeRecursive(pre, preStart + i - inStart + 1, preEnd,
                        in, i + 1, inEnd);
            }
        }
        return root;
    }

    private static class TreeNode {
        TreeNode left = null;
        TreeNode right = null;
        int val;

        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
     */
    public class Solution {
        Stack<Integer> stack1 = new Stack<Integer>();
        Stack<Integer> stack2 = new Stack<Integer>();

        public void push(int node) {
            stack1.push(node);
        }

        public int pop() {
            while (!stack1.isEmpty()) {
                stack2.push(stack1.pop());
            }
            int pop = -1;
            if (!stack2.isEmpty()) {
                pop = stack2.pop();
            }
            while (!stack2.isEmpty()) {
                stack1.push(stack2.pop());
            }
            return pop;
        }

        // 这是题解的方式，省略了拷贝回去的操作：
        public int pop2() {
            if (stack1.empty() && stack2.empty()) {
                throw new RuntimeException("Queue is empty!");
            }
            if (stack2.empty()) {
                while (!stack1.empty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }
    }

    /**
     * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
     * 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
     * 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
     * NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。
     */
    public int minNumberInRotateArray(int[] array) {
        // 要仔细阅读此题的题意：首先是有一个非递减（即递增）的序列，然后旋转该数组作为此方法的输入
        // 所以遍历虽然可以解决此问题，但是效率不高，因为旋转后的数组有部分子序列是有序的，
        // 所以此题的关键是考察二分法提高查找效率。
        if (array.length == 0) {
            return 0;
        }
        int left = 0, right = array.length - 1;
        while (left < right) {
            // 旋转后的序列其实存在两个有序的子序列，[left, right]区间内的元素一定满足：
            // array[left] > array[right]
            if (array[left] < array[right]) {
                return array[left];
            }
            int mid = left + (right - left) / 2;
            if (array[mid] > array[left]) {
                // 如果mid比left大，则left可以移到mid+1的位置缩小区间
                // 这里加1的话很有可能导致赋值后的left就是最小值的位置，
                // 不过没关系，每次while循环的开始我们已经判断了array[left] < array[right]
                left = mid + 1;
            } else if (array[mid] < array[right]) {
                // 这里不能减1，减1的话可能会把最小值给跳过去了。
                right = mid;
            } else {
                // 相等时，说明mid已经等于left了，因为二分最后的宿命就是mid=left。
                ++left;
            }
        }
        return array[left];
    }

    /**
     * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
     * n<=39
     */
    public int Fibonacci(int n) {
        // 斐波那契数列就是一个元素为前两个元素的和，比如：
        // 1、1、2、3、5、8、13、21、34
        // so，简单的递归一下就行了
        if (n == 0) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }
        return Fibonacci(n - 1) + Fibonacci(n - 2);
    }

    /**
     * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
     */
    public int JumpFloor(int target) {
        // 这题给的提示是递归，但是另一个比较经典的解决思路是动态规划，所以这里都实现一下。
        return jumpFloorDp(target);
    }

    private int jumpFloorDp(int n) {
        // dp(n) = dp(n-1)+dp(n-2)
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int dp2 = 1, dp1 = 2, dp = 0;
        for (int i = 3; i <= n; i++) {
            dp = dp2 + dp1;
            dp2 = dp1;
            dp1 = dp;
        }
        return dp;
    }

    private int jumpFloorRecursive(int target) {
        if (target <= 0) {
            return 0;
        }
        if (target == 1) {
            return 1;
        }
        if (target == 2) {
            return 2;
        }
        return jumpFloorRecursive(target - 1) + jumpFloorRecursive(target - 2);
    }
}
