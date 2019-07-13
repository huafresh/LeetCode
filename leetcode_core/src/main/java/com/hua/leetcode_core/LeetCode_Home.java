package com.hua.leetcode_core;

import java.util.HashMap;

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
}
