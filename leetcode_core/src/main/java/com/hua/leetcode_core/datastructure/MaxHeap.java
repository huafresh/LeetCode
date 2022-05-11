package com.hua.leetcode_core.datastructure;

/**
 * 最小堆
 *
 * @author zhangsh
 * @version V1.0
 * @date 2020-03-14 11:12
 */

public class MaxHeap {

    private int[] data;
    private int length;

    public MaxHeap(int[] data) {
        this.data = data;
        this.length = data.length;
        buildHeap();
    }

    private void buildHeap() {
        // 遍历每一个结点，然后把这个结点当成局部父结点，保证该局部二叉树满足最小堆特性。
        // 之所遍历从length/2开始，是因为最底部的叶子结点没有child，肯定不需要siftDown的。
        for (int i = length / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 对pos位置的元素执行向下调整。
     * 向下调整和向上调整是堆的基本操作。
     *
     * @param pos 数组索引，从0开始
     */
    private void siftDown(int pos) {
        while (true) {
            // 这个是二叉树线性存储后的特性，不信你画一画就知道了
            int leftChildPos = 2 * pos + 1;
            int rightChildPos = leftChildPos + 1;
            // 保存子节点较小值的index
            int childMaxPos = -1;
            if (leftChildPos < length && rightChildPos < length) {
                if (data[leftChildPos] < data[rightChildPos]) {
                    childMaxPos = rightChildPos;
                } else {
                    childMaxPos = leftChildPos;
                }
            } else if (leftChildPos < length) {
                childMaxPos = leftChildPos;
            } else if (rightChildPos < length) {
                childMaxPos = rightChildPos;
            }
            if (childMaxPos != -1) {
                if (data[childMaxPos] > data[pos]) {
                    int temp = data[pos];
                    data[pos] = data[childMaxPos];
                    data[childMaxPos] = temp;
                    pos = childMaxPos;
                    continue;
                }
            }
            break;
        }
    }

    private void siftUp(int pos) {
        // todo 对比siftDown
    }

    public int max() {
        if (length > 0) {
            int max = data[0];
            data[0] = data[length - 1];
            length--;
            siftDown(0);
            return max;
        }
        throw new IllegalStateException("heap is empty");
    }
}
