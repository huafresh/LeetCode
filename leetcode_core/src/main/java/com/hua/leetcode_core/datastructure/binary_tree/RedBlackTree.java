package com.hua.leetcode_core.datastructure.binary_tree;

import java.util.Stack;

/**
 * 红黑树
 *
 * @author hua
 * @version V1.0
 * @date 2019/2/18 16:47
 */

public class RedBlackTree<T extends Comparable<T>> extends SearchTree<T> {

    @Override
    public boolean insert(TreeNode<T> node) {
        RBTreeNode<T> insert = new RBTreeNode<>(node);
        //rbTreeNode.left = new RBTreeNode<>(null, true);
        //rbTreeNode.right = new RBTreeNode<>(null, true);
        super.insert(insert);
        fixUpInsert(insert);
        return true;
    }

    @Override
    public TreeNode<T> delete(TreeNode<T> node) {
        TreeNode<T> newNode = super.delete(node);
        if (newNode != null && newNode instanceof RedBlackTree.RBTreeNode) {
            fixUpDelete((RBTreeNode<T>) newNode);
        }

        return newNode;
    }

    private void fixUpDelete(RBTreeNode<T> node) {

        RBTreeNode<T> curNode = node;
        while (true) {
            //黑+红，直接设为黑色
            if (curNode.isRed()) {
                curNode.color = COLOR.BLACK;
                break;
            }

            //黑+黑，且为根，不调整
            if (curNode.isBlack() && curNode.parent == null) {
                break;
            }

            RBTreeNode<T> parent = (RBTreeNode<T>) curNode.parent;
            RBTreeNode<T> brother;
            if (isLeftChild(curNode, parent)) {
                brother = (RBTreeNode<T>) parent.right;
            } else {
                brother = (RBTreeNode<T>) parent.left;
            }
            RBTreeNode<T> brotherLeft = (RBTreeNode<T>) brother.left;
            RBTreeNode<T> brotherRight = (RBTreeNode<T>) brother.right;

            //处理四个case
            if (brother.isRed()) {
                brother.color = COLOR.BLACK;
                parent.setRed();
                leftRotate(parent);
            } else if (brother.isBlack() && brotherLeft.isBlack() && brotherRight.isBlack()) {
                brother.setRed();
                curNode = parent;
            } else if (brother.isBlack() && brotherLeft.isRed() && brotherRight.isBlack()) {
                brotherLeft.setBlack();
                brother.setRed();
                rightRotate(brother);
            } else if (brother.isBlack() && brotherRight.isRed()) {
                brother.color = parent.color;
                parent.setBlack();
                brotherRight.setBlack();
                leftRotate(parent);
            }
        }

        //checkRbTreeIllegal();

    }

    private void fixUpInsert(RBTreeNode<T> node) {
        RBTreeNode<T> curNode = node;
        while (curNode != null) {
            RBTreeNode<T> parent = (RBTreeNode<T>) curNode.parent;

            //根不需要调整
            if (parent == null) {
                curNode.setBlack();
                break;
            }

            if (curNode.color == null) {
                curNode.setRed();
            }

            //父结点为黑不用调整
            if (parent.color == COLOR.BLACK) {
                break;
            }

            RBTreeNode<T> uncle = findUncle(parent);
            RBTreeNode<T> grandParent = (RBTreeNode<T>) parent.parent;

            //三个case情况
            if (uncle.isRed()) {
                parent.color = COLOR.BLACK;
                uncle.color = COLOR.BLACK;
                grandParent.color = COLOR.RED;
                curNode = grandParent;
            } else if (uncle.isBlack() && !isLeftChild(curNode, parent)) {
                curNode = parent;
                leftRotate(curNode);
            } else if (uncle.isBlack() && isLeftChild(curNode, parent)) {
                parent.color = COLOR.BLACK;
                grandParent.color = COLOR.RED;
                rightRotate(grandParent);
            }
        }

        //checkRbTreeIllegal();
    }

    private int blackNum = -1;

    private void checkRbTreeIllegal() {
        if (root == null) {
            return;
        }

        RBTreeNode node = (RBTreeNode) root;
        if (node.isRed()) {
            return;
        }

        BaseTree.levelTraversal(root, new ITraversal<T>() {
            @Override
            public void onTraversal(TreeNode<T> curNode) {
                //把当前结点当成根结点进行后序遍历，回调的栈的内容就是页结点
                //到当前结点的路径
                blackNum = -1;
                SearchTree.behindTraversalWithStack(curNode, new ITraversalWithStack<T>() {
                    @Override
                    public void onTraversal(Stack<TreeNode<T>> stack, TreeNode<T> endNode) {
                        //判断结点为叶结点后统计栈中黑色结点的个数，如果出现不一致，则红黑树校验失败
                        if (endNode.left == null && endNode.right == null) {
                            Stack<TreeNode<T>> temp = new Stack<>();
                            int black = 0;
                            while (!stack.isEmpty()) {
                                TreeNode<T> pop = stack.pop();
                                if (pop instanceof RedBlackTree.RBTreeNode) {
                                    if (((RBTreeNode) pop).isBlack()) {
                                        black++;
                                    }
                                }
                                temp.push(pop);
                            }

                            if (blackNum == -1) {
                                blackNum = black;
                            } else {
                                if (blackNum != black) {
                                    throw new RuntimeException("RB tree check failed");
                                }
                            }

                            while (!temp.isEmpty()) {
                                stack.push(temp.pop());
                            }
                        }

                    }
                });

            }
        });

    }

    private RBTreeNode<T> findUncle(RBTreeNode<T> parent) {
        RBTreeNode<T> uncle;
        if (isLeftChild(parent, parent.parent)) {
            uncle = (RBTreeNode<T>) parent.parent.right;
        } else {
            uncle = (RBTreeNode<T>) parent.parent.left;
        }

        //普通的BST搜索树，uncle很有可能是空的，此时把它当作NIL结点
        if (uncle == null) {
            uncle = new RBTreeNode<T>(new TreeNode<T>(null), true);
        }

        return uncle;
    }

    private void leftRotate(TreeNode<T> node) {
        TreeNode<T> right = node.right;
        TreeNode<T> parent = node.parent;

        right.parent = parent;
        if (parent != null) {
            if (isLeftChild(node, parent)) {
                parent.left = right;
            } else {
                parent.right = right;
            }
        } else {
            //如果支点是根，则要修改root指针
            root = right;
        }
        node.parent = right;

        //当前结点作为右结点的左结点
        TreeNode<T> temp = right.left;
        right.left = node;
        node.right = temp;
    }

    private void rightRotate(TreeNode<T> node) {
        TreeNode<T> left = node.left;
        TreeNode<T> parent = node.parent;

        left.parent = parent;
        if (parent != null) {
            if (isLeftChild(node, parent)) {
                parent.left = left;
            } else {
                parent.right = left;
            }
        } else {
            root = left;
        }

        node.parent = left;
        TreeNode<T> temp = left.right;
        left.right = node;
        node.left = temp;
    }

    enum COLOR {
        RED,
        BLACK
    }

    static class RBTreeNode<T extends Comparable<T>> extends SearchTreeNode<T> {
        COLOR color;
        boolean isNIL;

        RBTreeNode(TreeNode<T> value) {
            this(value, false);
        }

        RBTreeNode(TreeNode<T> value, boolean nil) {
            super(value);
            this.isNIL = nil;
            if (nil) {
                color = COLOR.BLACK;
            }
        }

        @Override
        public String toString() {
            if (value != null) {
                return value.toString() + ":" + color;
            }
            return super.toString();
        }

        boolean isRed() {
            if (color == null) {
                throw new IllegalStateException("color is illegal");
            }
            return color == COLOR.RED;
        }

        boolean isBlack() {
            if (color == null) {
                throw new IllegalStateException("color is illegal");
            }
            return color == COLOR.BLACK;
        }

        void setRed() {
            color = COLOR.RED;
        }

        void setBlack() {
            color = COLOR.BLACK;
        }
    }

}
