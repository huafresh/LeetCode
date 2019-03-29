package com.hua.leetcode_core;

/**
 * 排序算法实现
 *
 * @author zhangsh
 * @version V1.0
 * @date 2019/3/29 10:14 AM
 */

public class SortUtil {

    /**
     * 归并排序
     *
     * @param a 待排序数组
     */
    public static void sortWithMerge(int[] a) {
        sortWithMergeRecursive(a, 0, a.length - 1);
    }

    private static void sortWithMergeRecursive(int[] a,
                                               int start, int end) {
        if (start == end) {
            return;
        }

        if (end - start == 1) {
            if (a[start] > a[end]) {
                int tmp = a[start];
                a[start] = a[end];
                a[end] = tmp;
            }
            return;
        }

        int middle = (start + end) / 2;
        sortWithMergeRecursive(a, start, middle);
        sortWithMergeRecursive(a, middle + 1, end);
        mergeArray(a, start, middle, end);

    }


    private static void mergeArray(int[] a, int start, int middle, int end) {
        if (start > middle || middle > end) {
            return;
        }
        int index = 0;
        int left = start;
        int right = middle + 1;
        int[] tempArray = new int[end - start + 1];

        while (left <= middle && right <= end) {
            if (a[left] < a[right]) {
                tempArray[index++] = a[left++];
            } else {
                tempArray[index++] = a[right++];
            }
        }

        while (left <= middle) {
            tempArray[index++] = a[left++];
        }

        while (right <= end) {
            tempArray[index++] = a[right++];
        }

        System.arraycopy(tempArray, 0, a, start, tempArray.length);
    }
}
