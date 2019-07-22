package com.hua.leetcode_core;

import java.util.HashMap;
import java.util.Map;
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
                nums[i] = nums[len-1];
                len--;
            } else {
                i++;
            }
        }
        return i;
    }
}
