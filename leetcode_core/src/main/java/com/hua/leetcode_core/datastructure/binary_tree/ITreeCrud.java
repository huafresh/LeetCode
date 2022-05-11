package com.hua.leetcode_core.datastructure.binary_tree;

/**
 * @author hua
 * @version V1.0
 * @date 2019/2/19 14:33
 */

interface ITreeCrud<T> {

    boolean insert(TreeNode<T> node);

    TreeNode<T> delete(TreeNode<T> node);
}
