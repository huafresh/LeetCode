package com.hua.leetcode_core;

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
}
