package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
        for (int i = len - 1; i >= 0; i--) {
            if (s.charAt(i) != ' ') {
                count++;
            } else {
                if (count != 0) {
                    break;
                }
            }
        }
        return count;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/plus-one/submissions/">加一</a>
     */
    public int[] plusOne(int[] digits) {
        // 此题的难点在于考虑进位的问题。
        // return plusOneIndex(digits,digits.length-1);

        // 以上是递归解法，略微有些复杂。。。
        // 看了题解发现针对此题其实循环就可以了。

        int len = digits.length;
        for (int i = len - 1; i >= 0; i--) {
            int plus = digits[i] + 1;
            if (plus > 9) {
                // 这里就是只用循环就可以的根本原因，因为题目要求加1，而进位也是加1，
                // 所以这里只需把i所在位置置0，下一次循环进位就会得到体现。
                digits[i] = 0;
            } else {
                digits[i] = plus;
                // 不需要进位了，直接返回
                return digits;
            }
        }
        int[] newDigits = new int[len + 1];
        //这里都不需要copy，因为进位到这里的话，只有高位是1，后面全是0。
        newDigits[0] = 1;
        return newDigits;
    }

    private int[] plusOneIndex(int[] digits, int index) {
        if (index == -1) {
            //已经进到最高位
            int[] newDigits = new int[digits.length + 1];
            newDigits[0] = 1;
            System.arraycopy(digits, 0, newDigits, 1, digits.length);
            return newDigits;
        }
        int plus = digits[index] + 1;
        if (plus > 9) {
            digits[index] = 0;
            return plusOneIndex(digits, index - 1);
        } else {
            //不需要进位
            digits[index] = plus;
            return digits;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/add-binary/submissions/">二进制求和</a>
     */
    public String addBinary(String a, String b) {

        int aLen = a.length();
        int bLen = b.length();
        int maxLen = Math.max(aLen, bLen);
        StringBuilder result = new StringBuilder();
        // 标识当前的计算是否需要额外加1。
        // 如果前面有进位，此值就会被置为true。
        boolean needAddOne = false;
        for (int i = 0; i < maxLen; i++) {
            // 先分别计算a、b相应位上的值，如果越界则为0。
            int aValue = 0;
            if (aLen - i - 1 >= 0) {
                // 这里不能用Integer.valueOf，因为会把char当成对应的ARSCII码来计算。
                aValue = a.charAt(aLen - i - 1) - '0';
            }
            int bValue = 0;
            if (bLen - i - 1 >= 0) {
                bValue = b.charAt(bLen - i - 1) - '0';
            }

            int sum = aValue + bValue + (needAddOne ? 1 : 0);
            result.append(sum % 2);
            needAddOne = (sum / 2) > 0;
        }

        if (needAddOne) {
            result.append(1);
        }

        return result.reverse().toString();
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/submissions/">二叉树的层次遍历 II</a>
     */
    @SuppressWarnings("ConstantConditions")
    public List<List<Integer>> levelOrderBottom(LeetCodeMain.TreeNode root) {
        // 仔细看返回值，其实倒序遍历不是重点，重点是给节点分层，分层的话可以用两个队列来做。
        // 后记：看评论区都没有双队列的解法，本来觉得双队列应该还比较巧妙的，
        // 结果打脸了，其实一个队列就够了，只需要在某次遍历的时候把所有元素取出来，
        // 刚好就是某一层的所有元素了，感觉这才是二叉树分层的标准思路吧，代码重写如下：
        final LinkedList<List<Integer>> results = new LinkedList<>();
        final Queue<LeetCodeMain.TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.add(root);
        }
        LeetCodeMain.TreeNode curNode = null;
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                curNode = queue.poll();
                list.add(curNode.val);
                if (curNode.left != null) {
                    queue.add(curNode.left);
                }
                if (curNode.right != null) {
                    queue.add(curNode.right);
                }
            }
            results.addFirst(list);
        }
        return results;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/submissions/">将有序数组转换为二叉搜索树</a>
     */
    public LeetCodeMain.TreeNode sortedArrayToBST(int[] nums) {
        // 数组已经是有序的，因此只需要每次取中间值填充二叉树即可。
        return binaryRoot(nums, 0, nums.length - 1);
    }

    private LeetCodeMain.TreeNode binaryRoot(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int half = (start + end) / 2;
        LeetCodeMain.TreeNode node = new LeetCodeMain.TreeNode(nums[half]);
        node.left = binaryRoot(nums, start, half - 1);
        node.right = binaryRoot(nums, half + 1, end);
        return node;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/balanced-binary-tree/">平衡二叉树</a>
     */
    public boolean isBalanced(LeetCodeMain.TreeNode root) {
        // 注意审题：是给定某个节点，然后看它的左右两个子树的高度差的绝对值。
        return treeHeight(root) != -1;
    }

    // 返回给定子树的高度，如果左右子树高度差大于1，则返回-1。
    // 返回-1的目的是在递归的过程中就把子树的高度差判断了。
    private int treeHeight(LeetCodeMain.TreeNode root){

        if(root == null){
            return 1;
        }

        int leftH = treeHeight(root.left);
        // 这里就做判断，避免leftH为-1时，右子树就没必要计算了。
        if(leftH == -1){
            return -1;
        }

        int rightH = treeHeight(root.right);
        if(rightH == -1){
            return -1;
        }

        int deltaH = Math.abs(leftH - rightH);
        if(deltaH>1){
            return -1;
        } else {
            return Math.max(leftH, rightH) + 1;
        }
    }
}
