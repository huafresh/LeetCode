package com.hua.leetcode_core.datastructure.binary_tree;


/**
 * 搜索树
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/18 14:55
 */
@SuppressWarnings("unchecked")
public class SearchTree<T extends Comparable<T>> extends BaseTree<T> {

    private int size = 0;

    @Override
    public boolean insert(TreeNode<T> node) {
        TreeNode<T> insert = node;
        if (!(node instanceof SearchTree.SearchTreeNode)) {
            insert = new SearchTreeNode<>(node);
        }

        if (root == null) {
            root = insert;
            return true;
        }

        TreeNode<T> curNode = root;
        while (true) {
            if (insert.value.compareTo(curNode.value) < 0) {
                if (curNode.left != null) {
                    curNode = curNode.left;
                } else {
                    insert.parent = curNode;
                    curNode.left = insert;
                    break;
                }
            } else {
                if (curNode.right != null) {
                    curNode = curNode.right;
                } else {
                    insert.parent = curNode;
                    curNode.right = insert;
                    break;
                }
            }
        }

        size++;

        return true;
    }

    @Override
    public TreeNode<T> delete(TreeNode<T> node) {
        if (root == null) {
            return null;
        }

        TreeNode<T> curNode = root;
        while (curNode != null) {
            if (node.equals(curNode)) {
                return deleteNode(curNode);
            }
            if (node.value.compareTo(curNode.value) < 0) {
                curNode = curNode.left;
            } else {
                curNode = curNode.right;
            }
        }

        return null;
    }

    /**
     * 删除结点，返回顶替结点。
     */
    private TreeNode<T> deleteNode(TreeNode<T> node) {
        TreeNode<T> parent = node.parent;

        //处理叶子结点情况
        if (node.left == null && node.right == null) {
            if (parent.value.compareTo(node.value) < 0) {
                parent.right = null;
            } else {
                parent.left = null;
            }
        }
        //处理左右孩子都存在情况
        else if (node.left != null && node.right != null) {
            //寻找直接前驱
            TreeNode<T> preNode = node.left;
            while (preNode.right != null) {
                preNode = preNode.right;
            }

            //删除直接前驱
            deleteNode(node);

            //直接前驱替换当前删除的结点
            preNode.parent = parent;
            if (parent.value.compareTo(node.value) < 0) {
                parent.right = preNode;
            } else {
                parent.left = preNode;
            }

            return preNode;
        }
        //处理仅有一个孩子的情况
        else {
            if (parent.value.compareTo(node.value) < 0) {
                if (node.left != null) {
                    parent.right = node.left;
                } else {
                    parent.right = node.right;
                }
                return parent.right;
            } else {
                if (node.left != null) {
                    parent.left = node.left;
                } else {
                    parent.left = node.right;
                }
                return parent.left;
            }
        }

        return null;
    }

    public static void sort(int[] data) {
        sortInternal(data, false);
    }

    private static int index = 0;

    private static void sortInternal(final int[] data, boolean isRb) {
        SearchTree<Integer> bsTree = null;
        if (!isRb) {
            bsTree = new SearchTree<>();
        } else {
            bsTree = new RedBlackTree<>();
        }

        for (int value : data) {
            bsTree.insert(new TreeNode<Integer>(value));
        }

        index = 0;
        BaseTree.middleTraversal(bsTree.root, new ITraversal<Integer>() {
            @Override
            public void onTraversal(TreeNode<Integer> node) {
                data[index++] = node.value;
            }
        });
    }

    public static void sortWithRBTree(int[] data) {
        sortInternal(data, true);
    }

    static class SearchTreeNode<T extends Comparable<T>> extends TreeNode<T> {

        SearchTreeNode(TreeNode<T> node) {
            super(node.value);
        }
    }

}
