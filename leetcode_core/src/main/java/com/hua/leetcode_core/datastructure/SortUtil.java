package com.hua.leetcode_core.datastructure;

/**
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-13 17:30
 */

public class SortUtil {

    /**
     * 冒泡排序
     */
    public static void bubbleSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                if (data[i] > data[j]) {
                    int temp = data[i];
                    data[i] = data[j];
                    data[j] = temp;
                }
            }
        }
    }

    /**
     * 选择排序：选择最小值置换到数组的前面
     */
    public static void selectSort(int[] data) {
        for (int i = 0; i < data.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < data.length; j++) {
                if (data[j] < data[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = data[i];
            data[i] = data[minIndex];
            data[minIndex] = temp;
        }
    }

    /**
     * 插入排序
     */
    public static void insertSort(int[] data) {
        for (int i = 1; i < data.length; i++) {
            int insert = data[i];
            for (int j = i - 1; j >= 0; j--) {
                if (data[j] > insert) {
                    // 大的往后移一位
                    data[j + 1] = data[j];
                    data[j] = insert;
                } else {
                    break;
                }
            }
        }
    }

    /**
     * 希尔排序
     */
    public static void shellSort(int[] data) {
        int h = 1;
        while (3 * h + 1 < data.length) {
            h = 3 * h + 1;
        }
        while (h > 0) {
            for (int off = 0; off < h; off++) {
                // 以下的两个for循环和上面的插入排序非常像，只不过就是增量不是1，而是h
                for (int i = h + off; i < data.length; i += h) {
                    int insert = data[i];
                    for (int j = i - h; j >= 0; j -= h) {
                        if (data[j] > insert) {
                            data[j + h] = data[j];
                            data[j] = insert;
                        } else {
                            break;
                        }
                    }
                }
            }
            h = (h - 1) / 3;
        }
    }

    /**
     * 快速排序
     */
    public static void quickSort(int[] data) {
        doQuickSortPart(data, 0, data.length - 1);
    }

    private static void doQuickSortPart(int[] data, int start, int end) {
        if (start >= end) {
            return;
        }
        int baseIndex = start + (end - start) / 2;
        int base = data[baseIndex];
        data[baseIndex] = data[end];
        int i = start, j = end;
        while (i != j) {
            while (i < j && data[i] <= base) {
                i++;
            }
            // i指针一直右移，有可能会等于j，因此这里要加判断
            if (i < j) {
                data[j] = data[i];
                // j的位置是空位，赋值完需减1
                j--;
            }
            while (i < j && data[j] > base) {
                j--;
            }
            if (i < j) {
                data[i] = data[j];
                i++;
            }
        }
        data[i] = base;
        doQuickSortPart(data, start, i);
        doQuickSortPart(data, i + 1, end);
    }

    /**
     * 归并排序
     */
    public static void mergeSort(int[] data) {
        doMergeSort(data, 0, data.length - 1);
    }

    private static void doMergeSort(int[] data, int start, int end) {
        if (start >= end) {
            return;
        }
        int half = start + (end - start) / 2;
        doMergeSort(data, start, half);
        doMergeSort(data, half + 1, end);
        // 上面递归完，[start, half]和[half+1, end]就是两个有序的子序列，因此下面合并之。
        // PS：关于数组的合并，大部分文章都是new辅助数组来实现的，因此咱也就别另辟蹊径了。
        int[] temp = new int[end - start + 1];
        int index = 0;
        int i = start, j = half + 1;
        while (i <= half && j <= end) {
            if (data[i] <= data[j]) {
                temp[index++] = data[i++];
            } else {
                temp[index++] = data[j++];
            }
        }
        while (i <= half) {
            temp[index++] = data[i++];
        }
        while (j <= end) {
            temp[index++] = data[j++];
        }
        for (int k = 0; k < temp.length; k++) {
            data[start + k] = temp[k];
        }
    }

    /**
     * 堆排序
     */
    public static void heapSort(int[] data) {
        MaxHeap heap = new MaxHeap(data);
        for (int i = (data.length - 1); i >= 0; i--) {
            data[i] = heap.max();
        }
    }

    /**
     * 桶排序
     */
    public static void bucketSort(int[] data) {
        // 建桶：即把bucket数组new出来
        int min = Integer.MAX_VALUE;
        for (int value : data) {
            min = Math.min(min, value);
        }
        int max = Integer.MIN_VALUE;
        for (int value : data) {
            max = Math.max(max, value);
        }
        int[] bucket = new int[max - min + 1];

        // 装桶：桶中的值就是每个元素出现的次数
        for (int value : data) {
            bucket[value - min]++;
        }

        // 重装桶：这个比较难理解，这个for循环后，每个桶中存放的就是每个元素在有序序列的index+1。
        // 这么说比较抽象，举个例子：
        // 序列：   5, 4, 3
        // 装桶后就是：
        // bucket: 0  1  2
        //         1  1  1
        // 重装后就是：
        // bucket: 0  1  2
        //         1  2  3
        // 那么此时，针对value=5, bucket[5-3]=3，因此5在有序序列的index=3-1=2。
        for (int i = 1; i < bucket.length; i++) {
            bucket[i] = bucket[i - 1] + bucket[i];
        }

        // 整理：即输出序列到临时数组
        int[] temp = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            temp[bucket[data[i] - min] - 1] = data[i];
            bucket[data[i] - min]--;
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = temp[i];
        }
    }

    /**
     * 基数排序
     */
    public static void radixSort(int[] data) {
        // 这里我们已知最大的数有三位
        for (int pos = 0; pos < 3; pos++) {
            Entry[] bucket = new Entry[10];
            // 分配
            for (int i = 0; i < data.length; i++) {
                int index = (int) (data[i] / Math.pow(10, pos) % 10);
                if (bucket[index] == null) {
                    bucket[index] = new Entry(data[i]);
                } else {
                    Entry curEntry = bucket[index];
                    while (curEntry.next != null) {
                        curEntry = curEntry.next;
                    }
                    curEntry.next = new Entry(data[i]);
                }
            }
            // 收集
            int index = 0;
            for (Entry entry : bucket) {
                Entry curEntry = entry;
                while (curEntry != null) {
                    data[index++] = curEntry.value;
                    curEntry = curEntry.next;
                }
            }
        }
    }

    static class Entry {
        private Entry next = null;
        private int value;

        Entry(int value) {
            this.value = value;
        }
    }
}
