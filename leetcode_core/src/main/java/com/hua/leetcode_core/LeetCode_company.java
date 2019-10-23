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
    private int treeHeight(LeetCodeMain.TreeNode root) {

        if (root == null) {
            return 1;
        }

        int leftH = treeHeight(root.left);
        // 这里就做判断，避免leftH为-1时，右子树就没必要计算了。
        if (leftH == -1) {
            return -1;
        }

        int rightH = treeHeight(root.right);
        if (rightH == -1) {
            return -1;
        }

        int deltaH = Math.abs(leftH - rightH);
        if (deltaH > 1) {
            return -1;
        } else {
            return Math.max(leftH, rightH) + 1;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/submissions/">二叉树的最小深度</a>
     */
    public int minDepth(LeetCodeMain.TreeNode root) {
        // 层序遍历没跑了，当发现叶子节点就停止遍历

        if (root == null) {
            return 0;
        }

        final Queue<LeetCodeMain.TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 1;
        out:
        while (!queue.isEmpty()) {
            int count = queue.size();
            for (int i = 0; i < count; i++) {
                LeetCodeMain.TreeNode node = queue.poll();
                if (node.left == null && node.right == null) {
                    break out;
                }
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            level++;
        }

        return level;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/path-sum/submissions/">路径总和</a>
     */
    public boolean hasPathSum(LeetCodeMain.TreeNode root, int sum) {
//         // 二叉树后序遍历时，栈中元素即为当前节点到根节点的路径
//         final Stack<Element> stack = new Stack<>();
//         TreeNode cur = root;
//         // 存储某一时刻栈中元素的和
//         int tempSum = 0;
//         while(cur != null || !stack.isEmpty()){
//             while(cur!=null){
//                 stack.push(new Element(cur));
//                 tempSum += cur.val;
//                 cur = cur.left;
//             }

//             if(!stack.isEmpty()){
//                 Element element = stack.pop();
//                 TreeNode node = element.node;
//                 if(element.isFirst){
//                     element.isFirst = false;
//                     stack.push(element);
//                     cur = node.right;
//                     tempSum += node.val;
//                 } else {
//                     if(node.left==null && node.right==null && tempSum==sum){
//                         return true;
//                     }
//                 }
//                 tempSum -= node.val;
//             }
//         }
//         return false;

        // 后记：以上后序遍历的解法把所有的可能路径都遍历出来了，对于此题来说有些小题大作，
        // 其实只需要递归就很容易解决：
        // PS: 注意边界条件，最开始的root为null时，且sum=0时应该返回false。
        if (root == null) {
            return false;
        }
        int remain = sum - root.val;
        if (root.left == null && root.right == null) {
            return remain == 0;
        }
        return hasPathSum(root.left, remain) || hasPathSum(root.right, remain);
    }

    private static class Element {
        private LeetCodeMain.TreeNode node;
        private boolean isFirst = true;

        private Element(LeetCodeMain.TreeNode node) {
            this.node = node;
        }
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/pascals-triangle/">杨辉三角</a>
     */
    public List<List<Integer>> generate(int numRows) {
        // 暴力一点的话，好像没什么难度啊
        final List<List<Integer>> resultList = new ArrayList<>(numRows);
        List<Integer> preList = null;
        for (int i = 0; i < numRows; i++) {
            final List<Integer> lineList = new ArrayList<>(i + 1);
            if (i == 0) {
                lineList.add(1);
            } else {
                for (int j = 0; j < i + 1; j++) {
                    // 边界特殊处理
                    if (j == 0) {
                        lineList.add(preList.get(0));
                    } else if (j == i) {
                        lineList.add(preList.get(j - 1));
                    } else {
                        lineList.add(preList.get(j - 1) + preList.get(j));
                    }
                }
            }
            resultList.add(lineList);
            preList = lineList;
        }
        return resultList;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/valid-palindrome/submissions/">验证回文串</a>
     */
    public boolean isPalindrome(String s) {
        // 两边一起遍历，遇到特殊字符或空格就忽略

        if (s == null || s.length() == 0) {
            return true;
        }

        int start = 0, end = s.length() - 1;

        while (start < end) {
            while (start < s.length() && !isValidChar(s.charAt(start))) {
                start++;
            }
            while (end >= 0 && !isValidChar(s.charAt(end))) {
                end--;
            }
            if (start < s.length() &&
                    end >= 0 &&
                    !isEqual(s.charAt(start), s.charAt(end))) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private boolean isValidChar(char c) {
        return isLetter(c) ||
                '0' <= c && c <= '9';
    }

    private boolean isEqual(char c1, char c2) {
        return c1 == c2 ||
                isLetter(c1) && isLetter(c2) && Math.abs(c1 - c2) == Math.abs('a' - 'A');
    }

    private static boolean isLetter(char c) {
        return 'a' <= c && c <= 'z' ||
                'A' <= c && c <= 'Z';
    }


    /**
     * <a href = "https://leetcode-cn.com/problems/single-number/submissions/">只出现一次的数字</a>
     */
    public int singleNumber(int[] nums) {
        // 线性时间复杂度那就表示只能遍历一遍，但是你要想知道后面是否有重复值就必须保存前面遍历到的值，
        // 但是这又使用了额外空间，所以难点就在这。
        // 像这种矛盾点，可以用DP的思想来处理，DP思想即：假设n已知，求n+1，
        // DP思想为什么会有线性时间是因为它在求最终结果的过程中不断利用已知的结果。
        // 针对这题就是假设[0, n]中只出现一次的元素是x，那么n+1时只需对比x和nums[n+1]是否相等就可以判断出
        // [0,n+1]这个区间是否还存在只出现一次的元素。
        // 把index=0用来存储最终的结果，就可以做到不使用额外空间了。避免麻烦我们还是用一个变量保存结果

        // 后记：上面bb了这么多，发现行不通，因为当n+1时x重复了，此时你无法知道下一个只出现一次的元素在哪里了。

        // 题解是用异或解决的，只能说666
        // 异或的含义就是相同为0，相异为1，因此对所有的元素异或，最终就会得到那个只出现一次的值。

        int result = nums[0];
        for (int i = 1; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /**
     * <a href = "https://leetcode-cn.com/problems/linked-list-cycle/submissions/">环形链表</a>
     */
    public boolean hasCycle(ListNode head) {
        // 如果可以更改Node这个类，这个题不难，只需增加变量标记Node是否被访问过即可。
        // 这里Leecode显然不会让你修改Node类。
        // 看了题解，可以用快慢指针求解，具体思路就是一快一慢两个指针，快的每次跑两个节点，慢的每次跑一个节点，
        // 起点都一样，如果存在环，那么快指针最终肯定会重新等于慢指针。

        ListNode fast = head;
        ListNode slow = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (fast == slow) {
                return true;
            }
        }

        return false;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

}
