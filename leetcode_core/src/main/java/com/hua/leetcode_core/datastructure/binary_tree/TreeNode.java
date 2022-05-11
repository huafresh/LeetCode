package com.hua.leetcode_core.datastructure.binary_tree;

import java.util.HashMap;

/**
 * 二叉树结点对象
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/19 14:25
 */

public class TreeNode<T> {

    TreeNode<T> parent;
    TreeNode<T> left;
    TreeNode<T> right;
    T value;
    private HashMap<String, Object> extras;

    public TreeNode(T value) {
        this.value = value;
    }

    void setExtra(String key, Object value) {
        if (extras == null) {
            extras = new HashMap<>();
        }
        extras.put(key, value);
    }

    Object getExtra(String key) {
        if (extras != null) {
            return extras.get(key);
        }
        return null;
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof TreeNode) {
            return value.equals(((TreeNode) o).value);
        }
        return super.equals(o);
    }
}
