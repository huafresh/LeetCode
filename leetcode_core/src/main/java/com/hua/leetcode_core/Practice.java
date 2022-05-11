package com.hua.leetcode_core;

//
// Created by clydeazhang on 2022/5/11 3:55 下午.
// Copyright (c) 2022 Tencent. All rights reserved.
//

/**
 * leetcode 学习专题
 */
public class Practice {

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

}
