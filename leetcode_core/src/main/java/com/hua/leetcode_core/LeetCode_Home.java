package com.hua.leetcode_core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * @author hua
 * @version V1.0
 * @date 2019/7/13 9:16
 */
public class LeetCode_Home {


    /**
     * <a href="https://leetcode-cn.com/problems/palindrome-number/comments/">整数回文数</a>
     */
    public boolean isPalindrome(int x) {
        //评论区看到一个简洁的解法，如下:
//        if (x < 0)
//            return false;
//        int rem = 0, y = 0;
//        int quo = x;
//        while (quo != 0) {
//            rem = quo % 10;
//            y = y * 10 + rem;
//            quo = quo / 10;
//        }
//        return y == x;

        //然后上述并未考虑y溢出的情况。

        //去掉边界条件
        if (x < 0 || (x % 10 == 0 && x != 0)) {
            return false;
        }

        int rev = 0;
        while (rev < x) {
            int end = x % 10;
            rev = rev * 10 + end;
            x /= 10;
        }

        //基于以上代码，考虑 x = 1234567的执行情况，
        //第一次循环就是: x = 123456, rev = 7
        //第二次循环就是: x = 12345, rev = 76
        //第三次循环就是: x = 1234, rev = 765
        //第四次循环就是: x = 123, rev = 7654
        //可以看到x的位数在不断变小，rev的位数在增加，
        //那么当循环结束，有rev >= x，
        //假设rev == x，毫无疑问，此时x就是回文数
        //假设rev > x，此时rev要不位数和x相同，要不就比x多一位（多两位就跳出循环了）
        //前者不用考虑，肯定不是回文数，对于后者只要 x == rev / 10，则x是回文数。
        //因为中间那一位不管是什么不影响结果。
        return rev == x || x == rev / 10;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/roman-to-integer/submissions/">罗马数字转整数</a>
     */
    public int romanToInt(String s) {
        //挨个字符取出来转换，特殊情况的话结合下一个字符进行考虑
        //第一版解法没啥亮点，看了题解，可以用Map来存储所有字符对应的值，good ideal!
        final HashMap<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);

        final int len = s.length();
        Integer result = 0;
        for (int i = 0; i < len; ) {
            int endIndex = Math.min(i + 2, len);
            String str = s.substring(i, endIndex);
            if (map.containsKey(str)) {
                result += map.get(str);
            } else {
                str = s.substring(i, i + 1);
                result += map.get(str);
            }
            i += str.length();
        }
        return result;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/longest-common-prefix/submissions/">最长公共前缀</a>
     */
    public String longestCommonPrefix(String[] strs) {
        final int size = strs.length;
        if (size == 0) {
            return "";
        }

        /*
         * 按列扫描，遇到某列有不相等的字符即停止
         */
        int index = 0;
        while (index < strs[0].length()) {
            char c = strs[0].charAt(index);
            for (int j = 1; j < size; j++) {
                if (index >= strs[j].length() || c != strs[j].charAt(index)) {
                    return strs[0].substring(0, index);
                }
            }
            index++;
        }
        return strs[0];
    }

    /**
     * <a href="https://leetcode-cn.com/problems/valid-parentheses/submissions/">有效的括号</a>
     */
    public boolean isValid(String s) {

        /*
         * 遇左括号则入栈，遇右括号则出栈，然后比较字符。
         * 注意边界条件，最后栈空才是有效的。
         */

        final Map<Character, Character> map = new HashMap<>(3);
        map.put('(', ')');
        map.put('{', '}');
        map.put('[', ']');

        final Stack<Character> stack = new Stack<>();
        int length = s.length();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty() || map.get(stack.pop()) != c) {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * <a href="https://leetcode-cn.com/problems/merge-two-sorted-lists/submissions/">合并两个有序链表</a>
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        /*
         * 注意理解题意，合并后要有序。
         * 考虑边界条件，l1, l2都有可能为空
         * 如果某个链表遍历完了，剩下的就不用遍历了，拼接在尾部即可。
         */

        ListNode node1 = l1;
        ListNode node2 = l2;
        ListNode root = new ListNode(-1);
        ListNode curNode = root;

        while (node1 != null && node2 != null) {

            if (node1.val < node2.val) {
                curNode.next = node1;
                node1 = node1.next;
            } else {
                curNode.next = node2;
                node2 = node2.next;
            }

            curNode = curNode.next;
        }

        curNode.next = node1 != null ? node1 : node2;

        return root.next;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/submissions/">删除排序数组中的重复项</a>
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        //第一版解法，运行123ms，好慢啊
        //int removedLen = 0;
        //for(int i=1;i<nums.length-removedLen;){
        //    if(nums[i]==nums[i-1]){
        //        //移除第i位
        //        for(int j=i;j<(nums.length - removedLen - 1);j++){
        //            nums[j] = nums[j+1];
        //        }
        //        removedLen++;
        //    } else {
        //        i++;
        //    }
        //}
        //return nums.length - removedLen;

        //双指针法，运行2ms，差距啊
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                //这里只拷贝了一次，要始终抓住的一点就是我们只关系i指针对应位置的数据是否正确
                //其他的地方的不需要管。
                nums[i] = nums[j];
            }
        }

        return i + 1;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/remove-element/">移除元素</a>
     */
    public int removeElement(int[] nums, int val) {

        /*
         * 删除i位置的元素，用末尾的值填充。
         */

        int i = 0;
        int len = nums.length;
        while (i < len) {
            if (nums[i] == val) {
                nums[i] = nums[len - 1];
                len--;
            } else {
                i++;
            }
        }
        return i;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/implement-strstr/submissions/">实现strStr()</a>
     */
    public int strStr(String haystack, String needle) {

        if (needle.length() == 0) {
            return 0;
        }

        //暴力求解法，双指针法

//        int i = 0, j = 0;
//        while (i < haystack.length() && j < needle.length()) {
//            if (haystack.charAt(i) != needle.charAt(j)) {
//                //指针回退
//                i = i - j + 1;
//                j = 0;
//            } else {
//                i++;
//                j++;
//            }
//        }
//        //考虑: [a]与[b]
//        if (j == needle.length()) {
//            return i - j;
//        }

        //由上可知，暴力解法下，每一次发现存在不匹配的字符时都回退到下一位
        //而没有利用好前面已经匹配的一些信息，
        //return -1;

        //KMP算法
        int[] nextArray = nextArray(needle);
        int i = 0, j = 0;
        while (i < haystack.length() && j < needle.length()) {
            if (haystack.charAt(i) != needle.charAt(j)) {
                //指针回退
                if (j > 0) {
                    //看一下笔记的那张图，这里是j-1
                    j = nextArray[j - 1];
                }
            } else {
                j++;
            }
            //i指针是一直往前走的，期间通过移动j指针来动态比较
            i++;
        }

        //考虑: [a]与[b]
        if (j == needle.length()) {
            return i - j;
        }

        return -1;
    }

    private int[] nextArray(String needle) {
        int len = needle.length();
        int[] nextArray = new int[len];
        for (int i = 0; i < len; i++) {
            int start = 0, end = i;
            while (start < end) {
                if (needle.charAt(start) != needle.charAt(end)) {
                    break;
                }
                start++;
                end--;
            }
            nextArray[i] = start;
        }
        return nextArray;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/search-insert-position/submissions/">搜索插入位置</a>
     */
    public int searchInsert(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start <= end) {
            // 这里不能直接相加再整出2，否则可能整型溢出
            // 为什么移位可以呢？因为即使溢出了，我们要得到的是和的一半
            // 那么和的高位是什么已经不重要了。
            int half = (start + end) >>> 1;
            if (target == nums[half]) {
                return half;
            } else if (target > nums[half]) {
                start = half + 1;
            } else if (target < nums[half]) {
                end = half - 1;
            }
        }
        //看了下JDK代码，这里返回的是 -(start + 1)，可能情景不太一样，后续解释下。
        return start;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/count-and-say/submissions/">报数</a>
     */
    public String countAndSay(int n) {

        /*
         * 关键就是读懂题意：
         * 即下一个数说出了上一个数的组成。
         * 例如上一个数是11，那上一个数就由2个1组成，所以下一个数是21；
         * 再例如上一个数是111221，就由3个1、2个2、1个1组成，那下一个数就是312211
         */

        if (n == 1) {
            return "1";
        }
        String last = countAndSay(n - 1);
        StringBuilder result = new StringBuilder();

        int len = last.length();
        for (int i = 0; i < len; ) {
            char c = last.charAt(i);
            int j = i + 1;
            while (j < len && c == last.charAt(j)) {
                j++;
            }
            //j-i就是字符的个数
            result.append(j - i).append(c);
            i = j;
        }

        return result.toString();
    }

    /**
     * <a href="https://leetcode-cn.com/problems/maximum-subarray/submissions/">最大子序和</a>
     * 解法参考：https://www.jianshu.com/p/2f1a64ce63f5
     */
    public int maxSubArray(int[] nums) {
        // 对于最大子序，有这样两个性质，即:
        // 从最左边往右任意个数，其和一定是正数，否则最大子序就可以抛弃该子子序而变得更大;
        // 从最左边往左任意个数，其和一定是负数，否则最大子序就可以包含该子子序而变得更大。

        // 扫描法: 时间复杂度O(n)
        // 从左到右遍历数组，并且累积元素的和，如果元素的和小于0，则累计清0。整个过程不断刷新最大
        // 的子序和，最后扫描结束就可以得到最大的子序和。

        int len = nums.length;
        int max = Integer.MIN_VALUE;
        // 临时记录当前子序列的和
        int sum = 0;
        for (int j = 0; j < len; j++) {
            sum += nums[j];
            // 要在这里比较，因为数组可能只有一个元素，并且还是负数。
            max = Math.max(max, sum);
            if (sum < 0) {
                /*
                 * 如果当前子序的和为负数了，那么当前子序就不可能是最大子序的一部分了，因此清0，重新累积。
                 *
                 * 解题的时候这里花了比较多的时间理解，主要的疑问如下:
                 * 为什么 [i,j] 的和为负数时，整段就可以直接抛弃了呢？
                 * 难道就不可以 [k,j] 的和为正数从而成为最大子序的一部分吗？(k在i、j之间)
                 * 答：还真的不可以。
                 * 我们反证法证明:
                 * 假设 [k,j] 的和为正数，那么由 [i,j] 的和为负数可知， [i,k] 的和一定为负数，如果这样，
                 * 那么我们在k处就会进到这里了，根本就不会累加到j处。
                 */
                sum = 0;
            }
        }
        return max;
    }

    public int maxSubArray2(int[] nums) {
        /*
         * 动态规划: 时间复杂度O(n)
         * 动态规划思想: 要求f(n)的值，可以假设f(n-1)已知，然后根据某种规律把它们联系起来。
         * 非常类似数学归纳法的证明过程：
         * 1、证明当 n=1 时命题成立。
         * 2、假设 n=m 时命题成立，那么可以推导出在 n=m+1 时命题也成立，则命题成立。
         *
         * 那么，针对此题，如何定义命题呢？
         * 首先，最大子序和一定是以数组中的某个元素结尾的(废话)，假设其索引为n，并且记f(n)为以n结尾
         * 的最大子序的和。那么f(n)和f(n-1)有如下关系:
         * f(n) = Math.max(f(n-1) + nums[n], nums[n])  n是数组索引
         * 当 n=0 时，显然f(n)是好计算的，因此根据上述公式不断增加n，我们就可以计算出所有的子序和，这个
         * 过程不断刷新最大和即可求得数组的最大子序和。
         */

        final int len = nums.length;
        int max = Integer.MIN_VALUE;
        for (int n = 0; n < len; n++) {
            // 临时记录f(n)
            int sum;
            if (n == 0) {
                // f(0)可以直接计算，不需要公式。
                sum = nums[0];
            } else {
                // 套用上面公式
                sum = Math.max(nums[n - 1] + nums[n], nums[n]);
            }
            max = Math.max(max, sum);
            // 我们需要利用好前面累加的和，这里直接借用原数组的空间存储，
            // 因为我们不会再重头遍历了。
            nums[n] = sum;
        }
        return max;

    }

    public int maxSubArray3(int[] nums) {
        /**
         * 分治法:
         * 目前对分治法的理解就是把大的问题拆分成小的问题，不断拆分直到最终小问题已知。
         * 针对此题，我们要求某个数组的最大子序，假设为 [i,j] ，然后我们把数组分成两部分，
         * [0,h]、[h+1,len]，那么最大子序 [i,j] 要不就落在 [0,h]，要不就落在 [h+1,len] ，
         * 要不就横跨两个部分。如果是横跨两个部分，那么我们只需用两个指针，从中间向两边遍历，
         * 即可求得最大子序和，而对于前面两种情况，则可以进一步拆分求解。
         */

        // TODO: 2019/8/17
        return 0;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/climbing-stairs/submissions/">爬楼梯</a>
     */
    public int climbStairs(int n) {
        // 用学到的DP思想，先假设f(n)已知，再根据题意求f(n+1)
        // 联系最大子序和那道题，可知要利用好前面已经求好的步数。
        // 如此O（n）时间复杂度即可解决此问题。

        // 保存f(n)的值
        int sum = 1;
        // 保存f(n-1)的值
        int sum2 = 0;
        for (int i = 2; i <= n; i++) {
            if (i == 2) {
                // dp解法一定是有一个触发点的，否则一直假设f(n-1)已知，
                // 那就没有尽头了。
                sum = 2;
                sum2 = 1;
            } else {
                // 前面sum个走法，每种走法走一步或两步就是新的走法
                int tmp = sum;
                sum = sum + sum2;
                sum2 = tmp;
            }
        }

        return sum;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/">删除排序链表中的重复元素</a>
     */
    public ListNode deleteDuplicates(ListNode head) {
        // 用快慢指针，好像做过这个题啊，，，
        // 后记: 也可以遍历比较元素的值，如果相等，就修改next指针为next.next

        ListNode slow, cur;
        slow = cur = head;
        while (cur != null) {
            if (cur.val != slow.val) {
                // 删除中间重复部分
                slow.next = cur;
                slow = cur;
            }
            cur = cur.next;
        }
        // 末尾可能重复
        if (slow != null) {
            slow.next = null;
        }

        return head;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/merge-sorted-array/submissions/">合并两个有序数组</a>
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 看了提示，原来可以从后面开始遍历，那么这就不用关心位置的问题了，
        // 更大的数直接追加到后面就好了。

        int i = m - 1, j = n - 1;
        int index = nums1.length - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[index--] = nums1[i--];
            } else {
                nums1[index--] = nums2[j--];
            }
        }

        while (i >= 0) {
            nums1[index--] = nums1[i--];
        }

        while (j >= 0) {
            nums1[index--] = nums2[j--];
        }
    }

    /**
     * <a href="https://leetcode-cn.com/problems/same-tree/submissions/>相同的树</a>
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // 层序遍历，挨个对比就行了

//         final Queue<TreeNode> pQueue = new LinkedList<>();
//         final Queue<TreeNode> qQueue = new LinkedList<>();

//         pQueue.add(p);
//         qQueue.add(q);

//         TreeNode pCurNode;
//         TreeNode qCurNode;
//         while(!pQueue.isEmpty() && !qQueue.isEmpty()){
//             pCurNode = pQueue.poll();
//             qCurNode = qQueue.poll();
//             if(pCurNode!=null && qCurNode!=null && pCurNode.val==qCurNode.val){
//                 pQueue.add(pCurNode.left);
//                 pQueue.add(pCurNode.right);
//                 qQueue.add(qCurNode.left);
//                 qQueue.add(qCurNode.right);
//             } else if( pCurNode!=null || qCurNode!=null){
//                 return false;
//             }
//         }

//         return pQueue.isEmpty() && qQueue.isEmpty();

        // 用递归再实现一下，看评论感觉很简洁
        if (p == null && q == null) {
            return true;
        } else if (p != null && q != null) {
            return p.val == q.val &&
                    isSameTree(p.left, q.left) &&
                    isSameTree(p.right, q.right);
        }
        return false;
    }

    /**
     * <a href="https://leetcode-cn.com/problems/same-tree/submissions/>对称二叉树</a>
     */
    public boolean isSymmetric(TreeNode root) {
        // 先来递归解法吧，感觉二叉树的所有问题都可以递归解决，
        // 因为二叉树本来就可以递归遍历。

        // if(root==null){
        //     return true;
        // } else {
        //     return _isSymmetric(root.left, root.right);
        // }

        // 看了题解，原理说明中说的迭代解法是指用队列，
        // 但是传统的层序遍历肯定是不行的，解决办法就是节点对称放入队列。
        if (root == null) {
            return true;
        }

        final Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root.left);
        queue.add(root.right);

        while (!queue.isEmpty()) {
            TreeNode node1 = queue.poll();
            TreeNode node2 = queue.poll();
            if (node1 != null && node2 != null &&
                    node1.val == node2.val) {
                // 对称入队列
                queue.add(node1.left);
                queue.add(node2.right);
                queue.add(node1.right);
                queue.add(node2.left);
            } else if (node1 != null || node2 != null) {
                return false;
            }
        }
        return true;
    }

    private boolean _isSymmetric(TreeNode p, TreeNode q) {
        if (p == null && q == null) {
            return true;
        } else if (p != null && q != null) {
            return p.val == q.val && _isSymmetric(p.left, q.right) && _isSymmetric(p.right, q.left);
        }
        return false;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * <a href="https://leetcode-cn.com/problems/pascals-triangle-ii/submissions/>杨辉三角 II</a>
     */
    public List<Integer> getRow(int rowIndex) {
        // dp思想: 假设第k-1行已知，求第k行
        // 后记: 评论区提到结果是对称的，看来还可以进一步优化减少运算

        int count = rowIndex + 1;

        final List<Integer> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(1);
        }
        for (int i = 0; i < count; i++) {
            // 难点在于不新增集合的基础上完成i+1的运算
            //暂存第j个值
            int temp = 1;
            for (int j = 0; j < i + 1; j++) {
                if (j == 0 || j == i) {
                    result.set(j, 1);
                } else {
                    int sum = temp + result.get(j);
                    temp = result.get(j);
                    result.set(j, sum);
                }
            }

        }

        return result;
    }
}
