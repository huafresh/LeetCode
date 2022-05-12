package com.hua.leetcode_core;

//
// Created by clydeazhang on 2022/5/11 3:55 下午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

/**
 * leetcode 学习专题
 */
public class Practice {

    public static void main(String[] args) {
        Practice practice = new Practice();
        int[] nums = new int[]{4, 0, 0, 0, 0, 0};
        int[] nums2 = new int[]{1, 2, 3, 5, 6};
        practice.merge(nums, 1, nums2, 5);
    }

    /**
     * 删除排序数组中的重复项: https://leetcode.cn/leetbook/read/top-interview-questions-easy/x2gy9m/
     */
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // 使用i, j快慢指针，可以减少移动次数
        int i = 0;
        int j = 1;
        while (i < nums.length && j < nums.length) {
            if (nums[i] != nums[j]) {
                nums[i + 1] = nums[j];
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i + 1;
    }

    /**
     * 合并两个有序的数组: https://leetcode.cn/leetbook/read/top-interview-questions-easy/xnumcr/
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // 两个数组都是升序排好序的，一开始就想到了双指针一直pk，然后把小点的值输出到数组
        // 为了不新分配内存空间，于是选择结果直接输出到nums1数组，但是碰到的问题是nums1数组前面的元素是有意义的。
        // 然后想到的是预处理把nums1数组前面的值拷贝到nums1数组后半段，然后再开始比较。
        // 后面看了题解真的是想抽自己耳光，为什么一定要pk选小值呢？从数组最后面开始往前遍历pk，
        // 选大的值输出到nums1数组后面不就行了吗。。。唉，思维受限啊。。。
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i];
                i--;
            } else {
                nums1[k--] = nums2[j];
                j--;
            }
        }
        while (i >= 0) {
            nums1[k--] = nums1[i--];
        }
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }

    }
}
