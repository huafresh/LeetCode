package com.hua.leetcode_core;

public class LeetCodeMain {

    public static void main(String[] args) {
        int[] array = new int[]{-9, -7, -5, -3, -1, 2, 4, 4, 7, 10};
        int[] result = new LeetCodeMain().sortedSquares(array);
        System.out.println("end sort");
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/jewels-and-stones/">宝石与石头</a>
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

    public static final int LOWER_CASE_MIN_VALUE = 0x61;
    public static final int LOWER_CASE_MAX_VALUE = 0x7A;
    public static final int UPPER_CASE_MIN_VALUE = 0x41;
    public static final int UPPER_CASE_MAX_VALUE = 0x5A;
    public static final int LOWER_UPPER_CASE_INTERVAL_VALUE = 0x20;

    /**
     * <a href = "https://leetcode-cn.com/problems/to-lower-case/submissions/">转换成小写字母</a>
     */
    public String toLowerCase(String str) {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isNeedTransform(c)) {
                c += LOWER_UPPER_CASE_INTERVAL_VALUE;
                builder.append(c);
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    private static boolean isNeedTransform(char c) {
        return isCharacter(c) && isUpperCase(c);
    }

    private static boolean isUpperCase(char c) {
        return c < LOWER_CASE_MIN_VALUE;
    }

    private static boolean isCharacter(char c) {
        return LOWER_CASE_MIN_VALUE <= c && c <= LOWER_CASE_MAX_VALUE ||
                UPPER_CASE_MIN_VALUE <= c && c <= UPPER_CASE_MAX_VALUE;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/squares-of-a-sorted-array/">有序数组的平方，即平方后排序</a>
     * 注意此题的关键并非排序。
     */
    public int[] sortedSquares(int[] a) {
        int len = a.length;
        int[] result = new int[len];

        //先找到最小的正数和最大的负数的位置
        int right = 0;
        while (right < len && a[right] < 0) {
            right++;
        }
        int left = right - 1;

        //向两边遍历，比较他们平方的大小，较小的输出到结果数组
        int index = 0;
        while (left >= 0 && right < len) {
            int leftSquare = a[left] * a[left];
            int rightSquare = a[right] * a[right];
            if (leftSquare < rightSquare) {
                result[index++] = leftSquare;
                left--;
            } else {
                result[index++] = rightSquare;
                right++;
            }
        }

        //最后走完剩下的数据
        while (left >= 0) {
            result[index++] = a[left] * a[left];
            left--;
        }

        while (right < len) {
            result[index++] = a[right] * a[right];
            right++;
        }


        return result;
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/delete-node-in-a-linked-list/">删除链表中的节点</a>
     * 说明：
     * 链表至少包含两个节点。
     * 链表中所有节点的值都是唯一的。
     * 给定的节点为非末尾节点并且一定是链表中的一个有效节点。
     * 不要从你的函数中返回任何结果。
     *
     * 批注：开始觉得是不是没给root节点，其实是故意的，正是此题用意。
     */
    public void deleteNode(ListNode node) {
//        ListNode curNode = node;
//        ListNode next = curNode.next;
//        ListNode pre = null;
//        while (next != null) {
//            curNode.val = next.val;
//            pre = curNode;
//            curNode = next;
//            next = curNode.next;
//        }
//
//        if (pre != null) {
//            pre.next = null;
//        }

        //以上注释是开始的解法，看了评论才发现其实没必要所有节点值都进行移动，
        //只需改变当前节点的next指向next的next就行了。
        node.val = node.next.val;
        node.next = node.next.next;


    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
